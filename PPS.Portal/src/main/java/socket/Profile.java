package socket;

import java.util.ArrayList;
import common.FactoryDao;
import common.IWorkflow;
import common.JsonConverter;
import common.NotificationType;
import common.Util;
import common.Workflow;
import dao.CompanyDao;
import dao.GroupDao;
import dao.UserDao;
import entity.NavigateNode;
import entity.SelectNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.UserBean;
import model.Password;
import model.User;
import reference.ActionRoleCache;
import reference.CardMaster;
import reference.RoleMaster;
import reference.StateMaster;

@Workflow(name = "profile", cardrole = CardMaster.PROFILE)
public class Profile extends IWorkflow {

	private static NavigateNode[] navi = null;
	private boolean passwordcheck;

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = getUserinfo(node.getSession()).getUser();
		UserBean data = new UserBean();
		data.setCompanyList(new ArrayList<>());
		data.setGroupList(new ArrayList<>());

		data.setGiven_name(user.getGivenName());
		data.setName(user.getName());
		data.setNick_name(user.getNickName());
		if (user.getImgBlob() == null) {
			data.setIs_img_blob(false);
			data.setImg_url(user.getImgUrl());
		} else {
			data.setIs_img_blob(true);
			data.setImg_blob(user.getImgBlob());
		}
		data.setCanModifyPassword(!StateMaster.equals(user.getStateInfo().getState(), StateMaster.getGoogleId()));
		data.setCanModifyCompany(ActionRoleCache.hasPermission(user, RoleMaster.getCompanyChange()));
		if (data.isCanModifyCompany()) {
			FactoryDao.getDao(CompanyDao.class).getCompanyAll().forEach(x -> {
				SelectNode select = new SelectNode();
				data.getCompanyList().add(select);
				select.setValue(String.valueOf(x.getId()));
				select.setName(x.getName());
			});
			data.setCompany(user.getCompany().getId());
		}
		data.setCanModifyGroup(ActionRoleCache.hasPermission(user, RoleMaster.getGroupChange()));
		if (data.isCanModifyGroup()) {
			FactoryDao.getDao(GroupDao.class).getGroupAll().forEach(x -> {
				SelectNode select = new SelectNode();
				data.getGroupList().add(select);
				select.setValue(String.valueOf(x.getId()));
				select.setName(x.getName());
			});
			data.setGroup(user.getGroup().getId());
		}

		return createWebSocketResult(data.toJson(), node);
	}

	public WebSocketResult apply(WebSocketNode node) {
		try {
			User user = getUserinfo(node.getSession()).getUser();
			Profile buffer = this;
			buffer.passwordcheck = true;
			JsonConverter.parse(node.getData(), (data) -> {
				if (Util.JsonIsKey(data, "current_password")
						&& !Util.StringIsEmptyOrNull(data.getString("current_password"))) {
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
					if (user.getPasswords() == null) {
						user.setPasswords(new ArrayList<>());
					}
					user.getPasswords().add(pwd);
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
					user.setCompany(
							FactoryDao.getDao(CompanyDao.class).getComany(Integer.parseInt(data.getString("company"))));
				}
				if (Util.JsonIsKey(data, "group")) {
					user.setGroup(
							FactoryDao.getDao(GroupDao.class).getGroup(Integer.parseInt(data.getString("group"))));
				}
			});
			if (!buffer.passwordcheck) {
				return createWebSocketResult(createNotification(NotificationType.Danger, "The password is incorrect."),
						node);
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
