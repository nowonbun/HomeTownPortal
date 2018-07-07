package contoller;

import java.util.List;

import org.apache.log4j.Logger;

import common.FactoryDao;
import common.IWorkflow;
import common.JsonConverter;
import common.LoggerManager;
import common.NotificationType;
import common.Workflow;
import dao.CompanyDao;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.ObjectBean;
import model.Company;
import model.Group;
import reference.CardMaster;
import reference.StateMaster;

@Workflow(name = "comgroupsetting", cardrole = CardMaster.COMPANY_N_GROUP_SETTING)
public class ComGroupSettingContoller extends IWorkflow {

	Logger logger = LoggerManager.getLogger(ComGroupSettingContoller.class);
	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode(CardMaster.getAdminCard()), new NavigateNode(CardMaster.getCompanyNGroupSettingCard()) };

	@Override
	public WebSocketResult init(WebSocketNode node) {
		ObjectBean data = new ObjectBean();
		data.setData("comgroupsetting");
		return createWebSocketResult(JsonConverter.create(data), node);
	}

	public WebSocketResult delete(WebSocketNode node) {
		try {
			String key = node.getData();
			String[] data = key.split("-");
			int comcode = Integer.valueOf(data[0]);
			int grpcode = Integer.valueOf(data[1]);
			Company com = FactoryDao.getDao(CompanyDao.class).getComany(comcode);
			List<Group> groups = com.<Company, Group>getLazyData(x -> x.getGroups());
			Group group = groups.stream().filter(x -> x.getId() == grpcode).findFirst().get();
			if (StateMaster.equals(StateMaster.getDefaultTransactionData(), group.getStateInfo().getState())) {
				return createNotificationResult(NotificationType.Danger, "The delete is impossible. because this is initial data.", node);
			}
			group.getStateInfo().setDelete(true);
			if (groups.stream().filter(x -> !x.getStateInfo().getIsDelete()).count() < 1) {
				if (!StateMaster.equals(StateMaster.getDefaultTransactionData(), com.getStateInfo().getState())) {
					com.getStateInfo().setDelete(true);
				}
			}
			FactoryDao.getDao(CompanyDao.class).update(com);
			return createNotificationResult(NotificationType.Success, "The data was updated.", node);
		} catch (Throwable e) {
			logger.error(e);
			logger.error("Data value : " + node.getData());
			return createNotificationResult(NotificationType.Danger, "The key data is wrong.", node);
		}

	}

	public WebSocketResult initAdd(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
