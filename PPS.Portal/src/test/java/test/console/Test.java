package test.console;

import common.FactoryDao;
import dao.CardDao;

public class Test {
	public static void main(String... arg) {
		byte[] data = new byte[1024];
		byte[] temp = "hello world".getBytes();
		for (int i = 0; i < temp.length; i++) {
			data[i] = temp[i];
		}
		String buffer = new String(data);
		System.out.println(buffer);
		CardDao dao = FactoryDao.getDao(CardDao.class);
		System.out.println(dao.getCard("ADMN"));
	}
}
