package socket;

import java.util.ArrayList;
import java.util.List;

import common.FactoryDao;
import common.IWorkflow;
import common.JsonConverter;
import common.NotificationType;
import common.Util;
import common.Workflow;
import dao.CompanyDao;
import dao.GroupDao;
import dao.RoleDao;
import dao.UserDao;
import entity.NavigateNode;
import entity.SelectNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import model.Password;
import model.Role;
import model.User;
import reference.RoleMaster;
import reference.StateMaster;

@Workflow(name = "profile")
public class Profile extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/profile", "Profile") };
	private boolean passwordcheck;

	@SuppressWarnings("unused")
	private class Node {
		String given_name;
		String name;
		String nick_name;
		boolean is_img_blob;
		String img_url;
		byte[] img_blob;
		boolean canModifyPassword;
		boolean canModifyCompany;
		boolean canModifyGroup;
		int company;
		int group;
		List<SelectNode> companyList;
		List<SelectNode> groupList;
		boolean passwordcheck;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = getUserinfo(node.getSession()).getUser();
		Node data = new Node();
		data.companyList = new ArrayList<>();
		data.groupList = new ArrayList<>();

		data.given_name = user.getGivenName();
		data.name = user.getName();
		data.nick_name = user.getNickName();
		if (user.getImgBlob() == null) {
			data.is_img_blob = false;
			data.img_url = user.getImgUrl();
		} else {
			data.is_img_blob = true;
			data.img_blob = user.getImgBlob();
		}
		data.canModifyPassword = !StateMaster.equals(user.getStateInfo().getState(), StateMaster.getGoogleId());
		List<Role> rolelist = FactoryDao.getDao(RoleDao.class).getRolebyUser(user);
		data.canModifyCompany = RoleMaster.has(rolelist, RoleMaster.getCompanyChange());
		if (data.canModifyCompany) {
			FactoryDao.getDao(CompanyDao.class).getCompanyAll().forEach(x -> {
				SelectNode select = new SelectNode();
				data.companyList.add(select);
				select.setValue(String.valueOf(x.getId()));
				select.setName(x.getName());
			});
			data.company = user.getCompany().getId();
		}
		data.canModifyGroup = RoleMaster.has(rolelist, RoleMaster.getCompanyChange());
		if (data.canModifyGroup) {
			FactoryDao.getDao(GroupDao.class).getGroupAll().forEach(x -> {
				SelectNode select = new SelectNode();
				data.groupList.add(select);
				select.setValue(String.valueOf(x.getId()));
				select.setName(x.getName());
			});
			data.group = user.getGroup().getId();
		}

		return createWebSocketResult(JsonConverter.create(data), node);
	}

	public WebSocketResult apply(WebSocketNode node) {
		try {
			User user = getUserinfo(node.getSession()).getUser();
			Profile buffer = this;
			buffer.passwordcheck = true;
			JsonConverter.parse(node.getData(), (data) -> {
				if (Util.JsonIsKey(data, "current_password") && !Util.StringIsEmptyOrNull(data.getString("current_password"))) {
					buffer.passwordcheck = false;
					String password = Util.convertMD5(data.getString("current_password"));
					for (Password item : user.getPasswords()) {
						if (item.getStateInfo().getIsDelete()) {
							continue;
						}
						if (item.getPassword().toUpperCase().equals(password.toUpperCase())) {
							buffer.passwordcheck = true;
							item.getStateInfo().setIsDelete(true);
							break;
						}
					}
					if (!buffer.passwordcheck) {
						return;
					}
					Password pwd = new Password(user, user.getName());
					pwd.setPassword(Util.convertMD5(data.getString("password")));
					user.addPassword(pwd);
				}
				if (Util.JsonIsKey(data, "given_name") && !Util.StringIsEmptyOrNull(data.getString("given_name"))) {
					user.setGivenName(data.getString("given_name"));
				}
				if (Util.JsonIsKey(data, "name") && !Util.StringIsEmptyOrNull(data.getString("name"))) {
					user.setName(data.getString("name"));
				}
				if (Util.JsonIsKey(data, "nick_name") && !Util.StringIsEmptyOrNull(data.getString("nick_name"))) {
					user.setNickName(data.getString("nick_name"));
				}
				if (Util.JsonIsKey(data, "is_img_blob")) {
					if (data.getBoolean("is_img_blob")) {
						user.setImgUrl(null);
						// TODO:blobimage
						user.setImgBlob(null);
					} else {
						user.setImgUrl(data.getString("img_url"));
						user.setImgBlob(null);
					}
				}

				if (Util.JsonIsKey(data, "company")) {
					user.setCompany(FactoryDao.getDao(CompanyDao.class).getComany(data.getInt("company")));
				}
				if (Util.JsonIsKey(data, "group")) {
					user.setGroup(FactoryDao.getDao(GroupDao.class).getGroup(data.getInt("group")));
				}
			});
			if (!buffer.passwordcheck) {
				return createWebSocketResult(createNotification(NotificationType.Danger, "The password is incorrect."), node);
			}
			FactoryDao.getDao(UserDao.class).update(user);
			return createWebSocketResult(createNotification(NotificationType.Success, "The profile is updated"), node);
		} catch (Throwable e) {
			return createWebSocketError(node);
		}
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
