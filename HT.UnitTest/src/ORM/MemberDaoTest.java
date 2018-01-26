package ORM;

import java.util.List;

import org.junit.Test;

import dao.FactoryDao;
import dao.UserInfoDao;
import model.UserInfo;

public class MemberDaoTest {
	
	@Test
	public void UserInfoDaoTest() {
		UserInfoDao dao = FactoryDao.getUserInfoDao();
		List<UserInfo> list = dao.findAll();
		
		for(UserInfo l : list) {
			System.out.println(l.getId().getId());
		}
	}
}
