package contoller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import common.FactoryDao;
import common.IWorkflow;
import common.JsonConverter;
import common.LoggerManager;
import common.NotificationType;
import common.Util;
import common.Workflow;
import dao.CompanyDao;
import dao.GroupDao;
import entity.NavigateNode;
import entity.SelectNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.ObjectBean;
import model.Company;
import model.Group;
import model.User;
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
		List<Company> list = FactoryDao.getDao(CompanyDao.class).getCompanyAll();
		List<SelectNode> ret = new ArrayList<>();
		for (Company com : list) {
			SelectNode bean = new SelectNode();
			bean.setName(com.getName());
			bean.setValue(String.valueOf(com.getId()));
			ret.add(bean);
		}
		return createWebSocketResult(JsonConverter.create(ret), node);
	}

	public WebSocketResult getGroup(WebSocketNode node) {
		int companyId = Integer.parseInt(node.getData());
		List<Group> list = FactoryDao.getDao(GroupDao.class).getGroupByCompany(companyId);
		List<SelectNode> ret = new ArrayList<>();
		for (Group group : list) {
			SelectNode bean = new SelectNode();
			bean.setName(group.getName());
			bean.setValue(String.valueOf(group.getId()));
			ret.add(bean);
		}
		return createWebSocketResult(JsonConverter.create(ret), node);
	}

	public WebSocketResult applyAdd(WebSocketNode node) {
		User user = getUserinfo(node.getSession()).getUser();
		if (JsonConverter.parse(node.getData(), (data) -> {
			if (!Util.JsonIsKey(data, "company") || Util.StringIsEmptyOrNull(data.getString("company"))) {
				return false;
			}
			if (!Util.JsonIsKey(data, "group") || Util.StringIsEmptyOrNull(data.getString("group"))) {
				return false;
			}
			Company company = FactoryDao.getDao(CompanyDao.class).getCompanyByName(data.getString("company"));
			if (company == null) {
				company = new Company(user.getId());
				company.setName(data.getString("company"));
				company.setGroups(new ArrayList<>());
			}
			for (Group group : company.getGroups()) {
				if (Util.StringEquals(group.getName(), data.getString("group"))) {
					return false;
				}
			}
			Group group = new Group(user.getId());
			group.setName(data.getString("group"));
			group.setCompany(company);
			company.getGroups().add(group);
			FactoryDao.getDao(CompanyDao.class).update(company);
			return true;
		})) {
			return createWebSocketResult(createNotification(NotificationType.Success, "The company and group is applied."), node);
		} else {
			return createWebSocketResult(createNotification(NotificationType.Success, "The company and group is fault to applied."), node);
		}
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
