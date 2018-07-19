package contoller;

import java.util.ArrayList;
import java.util.List;
import common.DBUtil;
import common.FactoryDao;
import common.IWorkflow;
import common.JsonConverter;
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
		List<SelectNode> list = new ArrayList<>();
		FactoryDao.getDao(CompanyDao.class).getCompanyAll().forEach(x -> {
			SelectNode select = new SelectNode();
			list.add(select);
			select.setValue(String.valueOf(x.getId()));
			select.setName(x.getName());
		});
		return createWebSocketResult(JsonConverter.create(list), node);
	}

	public WebSocketResult getGroup(WebSocketNode node) {
		List<SelectNode> list = new ArrayList<>();
		int key = Integer.parseInt(node.getData());
		SelectNode select = new SelectNode();
		list.add(select);
		select.setValue("0");
		select.setName("ALL");
		if (key != 0) {
			for (Group grp : FactoryDao.getDao(CompanyDao.class).getComany(key).getGroups()) {
				select = new SelectNode();
				list.add(select);
				select.setValue(String.valueOf(grp.getId()));
				select.setName(grp.getName());
			}
		}
		return createWebSocketResult(JsonConverter.create(list), node);
	}

	public WebSocketResult getUser(WebSocketNode node) {
		List<SelectNode> list = new ArrayList<>();
		int key = Integer.parseInt(node.getData());
		SelectNode select = new SelectNode();
		list.add(select);
		select.setValue("0");
		select.setName("ALL");
		if (key != 0) {
			for (User usr : FactoryDao.getDao(GroupDao.class).getGroup(key).getUsers()) {
				select = new SelectNode();
				list.add(select);
				select.setValue(usr.getId());
				select.setName(usr.getName());
			}
		}
		return createWebSocketResult(JsonConverter.create(list), node);
	}

	public WebSocketResult saveRole(WebSocketNode node) {
		System.out.println(node.getData());
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
