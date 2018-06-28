package common;

import javax.servlet.annotation.WebServlet;

import socket.Admin;
import socket.Card;
import socket.CardMasterSetting;
import socket.DataMasterSetting;
import socket.Login;
import socket.Profile;
import socket.UserManagement;
import socket.common.SocketBundleSet;

@WebServlet("/Startup")
public class Startup extends IServlet {
	private static final long serialVersionUID = 1L;

	public Startup() {
		super();
	}

	public void init() {
		SocketBundleSet.setClass(Admin.class);
		SocketBundleSet.setClass(Card.class);
		SocketBundleSet.setClass(Login.class);
		SocketBundleSet.setClass(Profile.class);
		SocketBundleSet.setClass(DataMasterSetting.class);
		SocketBundleSet.setClass(UserManagement.class);
		SocketBundleSet.setClass(CardMasterSetting.class);
		FactoryDao.initializeMaster();
	}

	public void doGet() {
		FactoryDao.resetMaster();
	}

	public void doPost() {
		FactoryDao.resetMaster();
	}
}
