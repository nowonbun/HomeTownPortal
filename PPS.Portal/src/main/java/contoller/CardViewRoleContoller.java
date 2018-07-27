package contoller;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue.ValueType;
import common.Controller;
import common.DBUtil;
import common.JsonConverter;
import common.NotificationType;
import common.Util;
import common.Workflow;
import entity.NavigateNode;
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
public class CardViewRoleContoller extends Controller {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode(CardMaster.getAdminCard()), new NavigateNode(CardMaster.getViewRoleCard()) };

	protected Class<?> setLogClass() {
		return CardViewRoleContoller.class;
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

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

	public WebSocketResult getCompany(WebSocketNode node) {
		ObjectBean bean = new ObjectBean();
		JsonConverter.parseObject(node.getData(), (data) -> {
			if (Util.JsonIsKey(data, "val") && !data.isNull("val")) {
				bean.setData(data.getInt("val"));
			}
			if (Util.JsonIsKey(data, "idx") && !data.isNull("idx")) {
				bean.setData3(data.getInt("idx"));
			}
		});
		bean.setData2(getSelectCompany());
		return createWebSocketResult(bean.toJson(), node);
	}

	public WebSocketResult getGroup(WebSocketNode node) {
		ObjectBean bean = new ObjectBean();
		JsonConverter.parseObject(node.getData(), (data) -> {
			if (Util.JsonIsKey(data, "val") && !data.isNull("val")) {
				bean.setData(data.getInt("val"));
			}
			if (Util.JsonIsKey(data, "idx") && !data.isNull("idx")) {
				bean.setData3(data.getInt("idx"));
			}
			bean.setData2(data.getInt("key"));
		});
		int key = (int) bean.getData2();
		bean.setData2(key != 0 ? getSelectGroup(key) : new ArrayList<>());
		return createWebSocketResult(bean.toJson(), node);
	}

	public WebSocketResult getUser(WebSocketNode node) {
		ObjectBean bean = new ObjectBean();
		JsonConverter.parseObject(node.getData(), (data) -> {
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
		int key = (int) bean.getData2();
		bean.setData2(key != 0 ? getSelectUser(key) : new ArrayList<>());
		return createWebSocketResult(bean.toJson(), node);
	}

	public WebSocketResult saveRole(WebSocketNode node) {
		List<RoleBean> listbean = new ArrayList<>();
		ObjectBean codebean = new ObjectBean();
		if (JsonConverter.parseObject(node.getData(), (data) -> {
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
				if (!Util.JsonStringIsEmptyOrNull(obj, "com")) {
					bean.setCompany(Integer.parseInt(obj.getString("com")));
				} else {
					continue;
				}
				if (!Util.JsonStringIsEmptyOrNull(obj, "grp")) {
					bean.setGroup(Integer.parseInt(obj.getString("grp")));
				} else {
					continue;
				}
				if (!Util.JsonStringIsEmptyOrNull(obj, "usr")) {
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
}
