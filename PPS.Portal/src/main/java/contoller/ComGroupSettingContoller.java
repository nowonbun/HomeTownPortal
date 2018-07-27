package contoller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import common.Controller;
import common.FactoryDao;
import common.JsonConverter;
import common.NotificationType;
import common.Util;
import common.Workflow;
import dao.CompanyDao;
import dao.GroupDao;
import entity.NavigateNode;
import entity.SelectNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.CompanyBean;
import entity.bean.ObjectBean;
import model.Company;
import model.Group;
import model.User;
import reference.CardMaster;
import reference.StateMaster;

@Workflow(name = "comgroupsetting", cardrole = CardMaster.COMPANY_N_GROUP_SETTING)
public class ComGroupSettingContoller extends Controller {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode(CardMaster.getAdminCard()), new NavigateNode(CardMaster.getCompanyNGroupSettingCard()) };

	protected Class<?> setLogClass() {
		return ComGroupSettingContoller.class;
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		ObjectBean data = new ObjectBean();
		data.setData("comgroupsetting");
		return createWebSocketResult(JsonConverter.create(data), node);
	}

	public WebSocketResult delete(WebSocketNode node) {
		try {
			User user = getUserinfo(node.getSession()).getUser();
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
			group.getStateInfo().setLastUpdate(new Date());
			group.getStateInfo().setLastUpdater(user.getId());
			group.getStateInfo().setDelete(true);
			if (groups.stream().filter(x -> !x.getStateInfo().getIsDelete()).count() < 1) {
				if (!StateMaster.equals(StateMaster.getDefaultTransactionData(), com.getStateInfo().getState())) {
					com.getStateInfo().setLastUpdate(new Date());
					com.getStateInfo().setLastUpdater(user.getId());
					com.getStateInfo().setDelete(true);
				}
			}
			FactoryDao.getDao(CompanyDao.class).update(com);
			return createNotificationResult(NotificationType.Success, "The data was updated.", node);
		} catch (Throwable e) {
			getLogger().error(e);
			getLogger().error("Data value : " + node.getData());
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

	public WebSocketResult applyEdit(WebSocketNode node) {
		User user = getUserinfo(node.getSession()).getUser();
		if (JsonConverter.parseObject(node.getData(), (data) -> {
			if (Util.JsonStringIsEmptyOrNull(data, "id")) {
				return false;
			}
			if (Util.JsonStringIsEmptyOrNull(data, "company")) {
				return false;
			}
			if (Util.JsonStringIsEmptyOrNull(data, "group")) {
				return false;
			}
			String[] index = data.getString("id").split("-");
			Company com = FactoryDao.getDao(CompanyDao.class).getComany(Integer.parseInt(index[0]));
			Group grp = com.<Company, Group>getLazyData(x -> x.getGroups()).stream().filter(x -> x.getId() == Integer.parseInt(index[1])).findFirst().get();
			com.setName(data.getString("company"));
			grp.setName(data.getString("group"));
			com.getStateInfo().setLastUpdate(new Date());
			com.getStateInfo().setLastUpdater(user.getId());
			com.getStateInfo().setIsDelete(false);
			grp.getStateInfo().setLastUpdate(new Date());
			grp.getStateInfo().setLastUpdater(user.getId());
			grp.getStateInfo().setIsDelete(false);
			FactoryDao.getDao(CompanyDao.class).update(com);
			return true;
		})) {
			return createWebSocketResult(createNotification(NotificationType.Success, "The company and group is modified."), node);
		} else {
			return createWebSocketResult(createNotification(NotificationType.Danger, "The company and group is fault to modified."), node);
		}
	}

	public WebSocketResult initEdit(WebSocketNode node) {
		String data = node.getData();
		String[] index = data.split("-");
		Company com = FactoryDao.getDao(CompanyDao.class).getComany(Integer.parseInt(index[0]));
		Group grp = com.<Company, Group>getLazyData(x -> x.getGroups()).stream().filter(x -> x.getId() == Integer.parseInt(index[1])).findFirst().get();
		CompanyBean bean = new CompanyBean();
		bean.setId(data);
		bean.setName(com.getName());
		bean.setGroupname(grp.getName());
		return createWebSocketResult(bean.toJson(), node);
	}

	public WebSocketResult applyAdd(WebSocketNode node) {
		User user = getUserinfo(node.getSession()).getUser();
		if (JsonConverter.parseObject(node.getData(), (data) -> {
			if (!Util.JsonStringIsEmptyOrNull(data, "company")) {
				return false;
			}
			if (!Util.JsonStringIsEmptyOrNull(data, "group")) {
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
			return createWebSocketResult(createNotification(NotificationType.Danger, "The company and group is fault to applied."), node);
		}
	}

}
