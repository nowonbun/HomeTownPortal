package ORM;


import org.junit.Test;

import dao.MasterDao;

public class InitTest {
	@Test
	public void InitTest() {
		MasterDao.initialize();
	}
}
