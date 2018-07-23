package contoller;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue.ValueType;

import common.DBUtil;
import common.FactoryDao;
import common.IWorkflow;
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
import entity.bean.ObjectBean;
import entity.bean.RoleBean;
import model.Card;
import model.Company;
import model.Group;
import model.User;
import reference.CardMaster;

@Workflow(name = "cardviewrole", cardrole = CardMaster.VIEW_ROLE)
public class CardViewRoleContoller extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode(CardMaster.getAdminCard()), new NavigateNode(CardMaster.getViewRoleCard()) };

	@Override
	public WebSocketResult init(WebSocketNode node) {
		ObjectBean data = new ObjectBean();
		data.setData("cardviewrole");
		return createWebSocketResult(JsonConverter.create(data), node);
	}

	public WebSocketResult editRole(WebSocketNode node) {
		String cardcode = node.getData();
		Card card = CardMaster.getDao().getCard(cardcode);

		List<Company> companys = DBUtil.getLazyData(card, (x -> x.getCompanies()));
		List<Group> groups = DBUtil.getLazyData(card, (x -> x.getGroups()));
		List<User> users = DBUtil.getLazyData(card, (x -> x.getUsers()));
		List<RoleBean> data = new ArrayList<>();

		for (Company com : companys) {
			List<Group> grps = getRoleGroup(com.getGroups(), groups);
			if (grps.size() == 0) {
				RoleBean bean = new RoleBean();
				bean.setCompany(com.getId());
				data.add(bean);
			}
			for (Group grp : grps) {
				List<User> usrs = getRoleUser(grp.getUsers(), users);
				if (usrs.size() == 0) {
					RoleBean bean = new RoleBean();
					bean.setCompany(com.getId());
					bean.setGroup(grp.getId());
					data.add(bean);
				}

				for (User usr : usrs) {
					if (users.contains(usr)) {
						RoleBean bean = new RoleBean();
						bean.setCompany(com.getId());
						bean.setGroup(grp.getId());
						bean.setUser(usr.getId());
						data.add(bean);
					}
				}
			}
		}

		return createWebSocketResult(JsonConverter.create(data), node);
	}

	private List<Group> getRoleGroup(List<Group> list, List<Group> role) {
		List<Group> ret = new ArrayList<>();
		for (Group grp : list) {
			if (role.contains(grp)) {
				ret.add(grp);
			}
		}
		return ret;
	}

	private List<User> getRoleUser(List<User> list, List<User> role) {
		List<User> ret = new ArrayList<>();
		for (User usr : list) {
			if (role.contains(usr)) {
				ret.add(usr);
			}
		}
		return ret;
	}

	public WebSocketResult getCompany(WebSocketNode node) {
		ObjectBean bean = new ObjectBean();
		JsonConverter.parse(node.getData(), (data) -> {
			if (Util.JsonIsKey(data, "val") && !data.isNull("val")) {
				bean.setData(data.getInt("val"));
			}
			if (Util.JsonIsKey(data, "idx") && !data.isNull("idx")) {
				bean.setData3(data.getInt("idx"));
			}
		});
		List<SelectNode> list = new ArrayList<>();
		for (Company com : FactoryDao.getDao(CompanyDao.class).getCompanyAll()) {
			SelectNode select = new SelectNode();
			list.add(select);
			select.setValue(String.valueOf(com.getId()));
			select.setName(com.getName());
		}
		bean.setData2(list);

		return createWebSocketResult(bean.toJson(), node);
	}

	public WebSocketResult getGroup(WebSocketNode node) {
		ObjectBean bean = new ObjectBean();
		JsonConverter.parse(node.getData(), (data) -> {
			if (Util.JsonIsKey(data, "val") && !data.isNull("val")) {
				bean.setData(data.getInt("val"));
			}
			if (Util.JsonIsKey(data, "idx") && !data.isNull("idx")) {
				bean.setData3(data.getInt("idx"));
			}
			bean.setData2(data.getInt("key"));
		});
		List<SelectNode> list = new ArrayList<>();
		int key = (int) bean.getData2();
		if (key != 0) {
			for (Group grp : FactoryDao.getDao(CompanyDao.class).getComany(key).getGroups()) {
				SelectNode select = new SelectNode();
				list.add(select);
				select.setValue(String.valueOf(grp.getId()));
				select.setName(grp.getName());
			}
		}
		bean.setData2(list);
		return createWebSocketResult(bean.toJson(), node);
	}

	public WebSocketResult getUser(WebSocketNode node) {
		ObjectBean bean = new ObjectBean();
		JsonConverter.parse(node.getData(), (data) -> {
			if (Util.JsonIsKey(data, "val") && !data.isNull("val")) {
				if (data.getValueType() == ValueType.STRING) {
					bean.setData(data.getString("val"));
				} else {
					bean.setData(data.getInt("val"));
				}
			}
			if (Util.JsonIsKey(data, "idx") && !data.isNull("idx")) {
				bean.setData3(data.getInt("idx"));
			}
			bean.setData2(data.getInt("key"));
		});
		List<SelectNode> list = new ArrayList<>();
		int key = (int) bean.getData2();
		if (key != 0) {
			for (User usr : FactoryDao.getDao(GroupDao.class).getGroup(key).getUsers()) {
				SelectNode select = new SelectNode();
				list.add(select);
				select.setValue(usr.getId());
				select.setName(usr.getName());
			}
		}
		bean.setData2(list);
		return createWebSocketResult(bean.toJson(), node);
	}

	public WebSocketResult saveRole(WebSocketNode node) {
		List<RoleBean> listbean = new ArrayList<>();
		ObjectBean codebean = new ObjectBean();
		if (JsonConverter.parse(node.getData(), (data) -> {
			if (Util.JsonIsKey(data, "key") && !Util.StringIsEmptyOrNull(data.getString("key"))) {
				codebean.setData(data.getString("key"));
			} else {
				return false;
			}
			if (!Util.JsonIsKey(data, "data")) {
				return false;
			}

			JsonArray list = data.getJsonArray("data");
			for (int i = 0; i < list.size(); i++) {
				JsonObject obj = (JsonObject) list.get(i);
				RoleBean bean = new RoleBean();
				bean.setCode(codebean.getData().toString());
				if (Util.JsonIsKey(obj, "com") && !Util.StringIsEmptyOrNull(obj.getString("com"))) {
					bean.setCompany(Integer.parseInt(obj.getString("com")));
				} else {
					continue;
				}
				if (Util.JsonIsKey(obj, "grp") && !Util.StringIsEmptyOrNull(obj.getString("grp"))) {
					bean.setGroup(Integer.parseInt(obj.getString("grp")));
				} else {
					continue;
				}
				if (Util.JsonIsKey(obj, "usr") && !Util.StringIsEmptyOrNull(obj.getString("usr"))) {
					bean.setUser(obj.getString("usr"));
				} else {
					continue;
				}
				listbean.add(bean);
			}
			return true;
		})) {
			Card card = CardMaster.getDao().getCard(codebean.getData().toString());
			CardMaster.getDao().deleteRole(card.getCode());
			List<Integer> coms = new ArrayList<>();
			List<Integer> grps = new ArrayList<>();
			List<String> usrs = new ArrayList<>();

			for (RoleBean bean : listbean) {
				if (bean.getCompany() == 0) {
					continue;
				}
				if (!coms.contains(bean.getCompany())) {
					coms.add(bean.getCompany());
				}
				if (bean.getGroup() == 0) {
					continue;
				}
				if (!grps.contains(bean.getGroup())) {
					grps.add(bean.getGroup());
				}
				if (Util.StringEquals(bean.getUser(), "0")) {
					continue;
				}
				if (!usrs.contains(bean.getUser())) {
					usrs.add(bean.getUser());
				}
			}
			CardMaster.getDao().setRole(card.getCode(), coms, grps, usrs);
			CardMaster.getDao().reset();
			return createWebSocketResult(createNotification(NotificationType.Success, "The role was setted."), node);
		} else {
			return createWebSocketResult(createNotification(NotificationType.Danger, "The role was error."), node);
		}
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
