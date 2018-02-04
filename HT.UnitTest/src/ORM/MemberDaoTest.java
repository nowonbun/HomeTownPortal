package ORM;

import java.util.List;

import org.junit.Test;

import dao.FactoryDao;
import dao.UserinfoDao;
import model.Userinfo;

public class MemberDaoTest {
	
	@Test
	public void UserInfoDaoTest() {
		UserinfoDao dao = FactoryDao.getUserInfoDao();
		List<Userinfo> list = dao.findAll();
		
		for(Userinfo l : list) {
			System.out.println(l.getId());
		}
	}
	
	@Test
	public void getUserTest() {
		UserinfoDao dao = FactoryDao.getUserInfoDao();
		Userinfo info = dao.getUser("test");
		System.out.println(info.getGivenname());
	}
}
