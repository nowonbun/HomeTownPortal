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
import entity.SelectNode;
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

@Workflow(name = "usermanagement", cardrole = CardMaster.USER_MANAGEMENT)
public class UserManagementContoller extends Controller {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode(CardMaster.getAdminCard()), new NavigateNode(CardMaster.getUserManagementCard()) };

	protected Class<?> setLogClass() {
		return ProfileContoller.class;
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		ObjectBean data = new ObjectBean();
		data.setData("userlist");
		return createWebSocketResult(JsonConverter.create(data), node);
	}

	public WebSocketResult initAdd(WebSocketNode node) {
		UserBean data = new UserBean();
		data.setCompanyList(getSelectCompany());
		data.setGroupList(new ArrayList<>());
		return createWebSocketResult(data.toJson(), node);
	}

	public WebSocketResult addUser(WebSocketNode node) {
		try {
			User sessionuser = getUserinfo(node.getSession()).getUser();
			if (JsonConverter.parseObject(node.getData(), (data) -> {
				if (FactoryDao.getDao(UserDao.class).getUser(Util.JsonString(data, "uid")) != null) {
					return false;
				}
				User user = new User(sessionuser.getName(), StateMaster.getPrivateId());
				user.setId(Util.JsonString(data, "uid"));
				Password pwd = new Password(user, sessionuser.getName());
				pwd.setPassword(Util.convertMD5(Util.JsonString(data, "password")));
				user.setPasswords(new ArrayList<>());
				user.getPasswords().add(pwd);
				user.setGivenName(Util.JsonString(data, "given_name"));
				user.setName(Util.JsonString(data, "name"));
				user.setNickName(Util.JsonString(data, "nick_name"));
				user.setImgBlob(Util.JsonBytes(data, "img_blob"));
				user.setCompany(FactoryDao.getDao(CompanyDao.class).getComany(Integer.parseInt(Util.JsonString(data, "company"))));
				user.setGroup(FactoryDao.getDao(GroupDao.class).getGroup(Integer.parseInt(Util.JsonString(data, "group"))));
				FactoryDao.getDao(UserDao.class).update(user);
				return true;
			})) {
				return createWebSocketResult(createNotification(NotificationType.Success, "The user was added."), node);
			} else {
				return createWebSocketResult(createNotification(NotificationType.Danger, "Update failed."), node);
			}
		} catch (Throwable e) {
			return createWebSocketResult(node);
		}
	}

	public WebSocketResult checkUid(WebSocketNode node) {
		String uid = node.getData();
		ObjectBean bean = new ObjectBean();
		if (FactoryDao.getDao(UserDao.class).getUser(uid) != null) {
			bean.setData(true);
		} else {
			bean.setData(false);
		}
		return createWebSocketResult(bean.toJson(), node);
	}

	public WebSocketResult initEdit(WebSocketNode node) {
		String id = node.getData();
		User user = FactoryDao.getDao(UserDao.class).getUser(id);
		if (user == null) {
			return createWebSocketResult(createNotification(NotificationType.Danger, "The user is not exist."), node);
		}
		User sessionuser = getUserinfo(node.getSession()).getUser();
		UserBean data = new UserBean();
		data.setCompanyList(new ArrayList<>());
		data.setGroupList(new ArrayList<>());

		data.setGiven_name(user.getGivenName());
		data.setName(user.getName());
		data.setNick_name(user.getNickName());
		if (user.getImgBlob() != null) {
			data.setImg_blob(new String(user.getImgBlob()));
		}
		data.setCanModifyPassword(!StateMaster.equals(user.getStateInfo().getState(), StateMaster.getGoogleId()));
		data.setCanModifyCompany(ActionRoleCache.hasPermission(sessionuser, RoleMaster.getCompanyChange()));
		if (data.isCanModifyCompany()) {
			FactoryDao.getDao(CompanyDao.class).getCompanyAll().forEach(x -> {
				SelectNode select = new SelectNode();
				data.getCompanyList().add(select);
				select.setValue(String.valueOf(x.getId()));
				select.setName(x.getName());
			});
			data.setCompany(user.getCompany().getId());
		}
		data.setCanModifyGroup(ActionRoleCache.hasPermission(sessionuser, RoleMaster.getGroupChange()));
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

	public WebSocketResult delete(WebSocketNode node) {
		try {
			String id = node.getData();
			User user = FactoryDao.getDao(UserDao.class).getUser(id);
			if (user == null) {
				return createWebSocketResult(createNotification(NotificationType.Danger, "The user is not exist."), node);
			}
			String idconvert = user.getId() + "_" + Util.createCookieKey();
			user.setId(idconvert);
			user.getStateInfo().setIsDelete(true);
			FactoryDao.getDao(UserDao.class).update(user);
			return createNotificationResult(NotificationType.Success, "The data was updated.", node);
		} catch (Throwable e) {
			getLogger().error(e);
			getLogger().error("Data value : " + node.getData());
			return createNotificationResult(NotificationType.Danger, "The key data is wrong.", node);
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

	public WebSocketResult applyEdit(WebSocketNode node) {
		try {
			if (JsonConverter.parseObject(node.getData(), (data) -> {
				User user = FactoryDao.getDao(UserDao.class).getUser(Util.JsonString(data, "uid"));
				if (user == null) {
					return false;
				}
				if (!Util.JsonStringIsEmptyOrNull(data, "current_password")) {
					boolean passwordcheck = false;
					String password = Util.convertMD5(Util.JsonString(data, "current_password"));
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
				user.setGivenName(Util.JsonString(data, "given_name"));
				user.setName(Util.JsonString(data, "name"));
				user.setNickName(Util.JsonString(data, "nick_name"));
				user.setImgBlob(Util.JsonBytes(data, "img_blob"));
				if (Util.JsonIsKey(data, "company")) {
					user.setCompany(FactoryDao.getDao(CompanyDao.class).getComany(Util.JsonInteger(data, "company")));
				}
				if (Util.JsonIsKey(data, "group")) {
					user.setGroup(FactoryDao.getDao(GroupDao.class).getGroup(Util.JsonInteger(data, "group")));
				}
				FactoryDao.getDao(UserDao.class).update(user);
				return true;
			})) {
				return createWebSocketResult(createNotification(NotificationType.Success, "The user information is updated"), node);
			} else {
				return createWebSocketResult(createNotification(NotificationType.Danger, "The user information is incorrect."), node);
			}

		} catch (Throwable e) {
			getLogger().error(e);
			return createWebSocketError(node);
		}
	}
}
