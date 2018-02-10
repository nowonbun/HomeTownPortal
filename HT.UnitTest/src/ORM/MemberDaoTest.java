package ORM;

import java.util.List;

import org.junit.Test;

import dao.FactoryDao;
import dao.UserDao;
import model.User;

public class MemberDaoTest {
	
	@Test
	public void UserInfoDaoTest() {
		UserDao dao = FactoryDao.getDao(UserDao.class);
		List<User> list = dao.findAll();
		
		for(User l : list) {
			System.out.println(l.getId());
		}
	}
	
	@Test
	public void getUserTest() {
		UserDao dao = FactoryDao.getDao(UserDao.class);
		User info = dao.getUser("test");
		System.out.println(info.getGivenName());
	}
}
