package contoller;

import java.util.ArrayList;
import common.Controller;
import common.FactoryDao;
import common.JsonConverter;
import common.NotificationType;
import common.Util;
import common.Workflow;
import dao.CompanyDao;
import dao.GroupDao;
import dao.UserDao;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.ObjectBean;
import entity.bean.UserBean;
import model.Password;
import model.User;
import reference.ActionRoleCache;
import reference.CardMaster;
import reference.RoleMaster;
import reference.StateMaster;

@Workflow(name = "profile", cardrole = CardMaster.PROFILE)
public class ProfileContoller extends Controller {

	protected Class<?> setLogClass() {
		return ProfileContoller.class;
	}

	@Override
	protected NavigateNode[] navigation() {
		return null;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = getUserinfo(node.getSession()).getUser();
		UserBean data = new UserBean();
		data.setGiven_name(user.getGivenName());
		data.setName(user.getName());
		data.setNick_name(user.getNickName());
		if (user.getImgBlob() == null) {
			data.setIs_img_blob(false);
			data.setImg_url(user.getImgUrl());
		} else {
			data.setIs_img_blob(true);
			data.setImg_blob(new String(user.getImgBlob()));
		}
		data.setCanModifyPassword(!StateMaster.equals(user.getStateInfo().getState(), StateMaster.getGoogleId()));
		data.setCanModifyCompany(ActionRoleCache.hasPermission(user, RoleMaster.getCompanyChange()));
		if (data.isCanModifyCompany()) {
			data.setCompanyList(getSelectCompany());
			data.setCompany(user.getCompany().getId());
		}
		data.setCanModifyGroup(ActionRoleCache.hasPermission(user, RoleMaster.getGroupChange()));
		if (data.isCanModifyGroup()) {
			data.setGroupList(getSelectGroup(user.getCompany().getId()));
			data.setGroup(user.getGroup().getId());
		}

		return createWebSocketResult(data.toJson(), node);
	}

	public WebSocketResult apply(WebSocketNode node) {
		try {
			User user = getUserinfo(node.getSession()).getUser();
			if (JsonConverter.parseObject(node.getData(), (data) -> {
				if (Util.JsonIsKey(data, "current_password") && !Util.StringIsEmptyOrNull(data.getString("current_password"))) {
					boolean passwordcheck = false;
					String password = Util.convertMD5(data.getString("current_password"));
					for (Password item : user.getPasswords()) {
						if (item.getStateInfo().getIsDelete()) {
							continue;
						}
						if (item.getPassword().toUpperCase().equals(password.toUpperCase())) {
							passwordcheck = true;
							item.getStateInfo().setIsDelete(true);
							break;
						}
					}
					if (!passwordcheck) {
						return false;
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
						user.setImgBlob(data.getString("img_url").getBytes());
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
				return true;
			})) {
				FactoryDao.getDao(UserDao.class).update(user);
				return createWebSocketResult(createNotification(NotificationType.Success, "The profile is updated"), node);
			} else {
				return createWebSocketResult(createNotification(NotificationType.Danger, "The password is incorrect."), node);
			}

		} catch (Throwable e) {
			getLogger().error(e);
			return createWebSocketError(node);
		}
	}

	public WebSocketResult getGroup(WebSocketNode node) {
		int key = JsonConverter.parseObject(node.getData(), (data) -> {
			return data.getInt("key");
		});
		ObjectBean bean = new ObjectBean();
		bean.setData(key != 0 ? getSelectGroup(key) : new ArrayList<>());
		return createWebSocketResult(bean.toJson(), node);
	}
}
