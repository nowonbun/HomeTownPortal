package test.dao;

import dao.ApplicationDao;
import dao.FactoryDao;
import dao.UserDao;
import model.Application;
import reference.StateMaster;

public class ApplicationDaoTest {
	public static void main(String... arg) {
		ApplicationDao dao = FactoryDao.getDao(ApplicationDao.class);
		UserDao userdao = FactoryDao.getDao(UserDao.class);
		Application app = new Application(userdao.getUser("admin"), "TEST");
		app.getStateInfo().setState(StateMaster.getApplying());
		dao.create(app);
	}
}
