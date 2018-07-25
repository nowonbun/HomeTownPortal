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

	//TODO :: This function need be check that current  ID exist.
	public WebSocketResult addUser(WebSocketNode node) {
		try {
			User sessionuser = getUserinfo(node.getSession()).getUser();
			JsonConverter.parseObject(node.getData(), (data) -> {
				User user = new User(sessionuser.getName(), StateMaster.getPrivateId());
				user.setId(data.getString("uid"));
				Password pwd = new Password(user, sessionuser.getName());
				pwd.setPassword(Util.convertMD5(data.getString("password")));
				user.setPasswords(new ArrayList<>());
				user.getPasswords().add(pwd);
				user.setGivenName(data.getString("given_name"));
				user.setName(data.getString("name"));
				user.setNickName(data.getString("nick_name"));
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
				user.setCompany(FactoryDao.getDao(CompanyDao.class).getComany(Integer.parseInt(data.getString("company"))));
				user.setGroup(FactoryDao.getDao(GroupDao.class).getGroup(Integer.parseInt(data.getString("group"))));
				FactoryDao.getDao(UserDao.class).update(user);
			});
			return createWebSocketResult(createNotification(NotificationType.Success, "The user was added"), node);
		} catch (Throwable e) {
			return createWebSocketError(node);
		}
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
		if (user.getImgBlob() == null) {
			data.setIs_img_blob(false);
			data.setImg_url(user.getImgUrl());
		} else {
			data.setIs_img_blob(true);
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

	public WebSocketResult initDelete(WebSocketNode node) {
		return createWebSocketResult(node);
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
