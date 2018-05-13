package ORM;

import java.util.List;

import org.junit.Test;

import common.FactoryDao;
import common.MasterDao;
import dao.CardDao;
import dao.UserDao;
import model.Card;
import model.User;

public class CardViewRole {
	@Test
	public void permissionTest() {
		MasterDao.initialize();
		User user = FactoryDao.getDao(UserDao.class).findOne("admin");
		List<Card> cards = FactoryDao.getDao(CardDao.class).getCardbyUser(user);
		for(Card card : cards) {
			System.out.println(card.getCode());
		}
	}
}
