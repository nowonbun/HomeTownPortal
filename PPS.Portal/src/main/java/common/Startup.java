package common;

import javax.servlet.annotation.WebServlet;

import contoller.ActionRoleController;
import contoller.AdminContoller;
import contoller.CardContoller;
import contoller.CardMasterSettingController;
import contoller.CardViewRoleContoller;
import contoller.ComGroupSettingContoller;
import contoller.DataMasterSettingContoller;
import contoller.LoginContoller;
import contoller.MainContoller;
import contoller.ProfileContoller;
import contoller.UserManagementContoller;
import reference.ActionRoleCache;
import reference.CardRoleCache;
import socket.common.SocketBundleSet;

@WebServlet("/Startup")
public class Startup extends IServlet {
	private static final long serialVersionUID = 1L;

	public Startup() {
		super();
	}

	public void init() {
		SocketBundleSet.setClass(AdminContoller.class);
		SocketBundleSet.setClass(CardContoller.class);
		SocketBundleSet.setClass(LoginContoller.class);
		SocketBundleSet.setClass(ProfileContoller.class);
		SocketBundleSet.setClass(DataMasterSettingContoller.class);
		SocketBundleSet.setClass(UserManagementContoller.class);
		SocketBundleSet.setClass(CardMasterSettingController.class);
		SocketBundleSet.setClass(ActionRoleController.class);
		SocketBundleSet.setClass(CardViewRoleContoller.class);
		SocketBundleSet.setClass(ComGroupSettingContoller.class);
		SocketBundleSet.setClass(MainContoller.class);
		FactoryDao.initializeMaster();
		CardRoleCache.reset();
		ActionRoleCache.reset();
	}

	public void doGet() {
		FactoryDao.resetMaster();
	}

	public void doPost() {
		FactoryDao.resetMaster();
	}
}
