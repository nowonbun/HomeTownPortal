package reference;

import common.FactoryDao;
import dao.CardTypeDao;
import model.CardType;

public class CardTypeMaster {
	public static String EVENT_CARD = "EVT";
	public static String IMAGE_CARD = "IMG";
	public static String MOMAL_CARD = "MDL";
	public static String EVENT_MENU_CARD = "EMC";
	public static String IMAGE_MENU_CARD = "IMC";

	public static CardTypeDao getDao() {
		return FactoryDao.getDao(CardTypeDao.class);
	}
	
	public static CardType getImageMenuCardType() {
		return getDao().getCardType(IMAGE_MENU_CARD);
	}
	
	public static CardType getEventMenuCardType() {
		return getDao().getCardType(EVENT_MENU_CARD);
	}

	public static CardType getEventCardType() {
		return getDao().getCardType(EVENT_CARD);
	}

	public static CardType getImageCardType() {
		return getDao().getCardType(IMAGE_CARD);
	}

	public static CardType getModalCardType() {
		return getDao().getCardType(MOMAL_CARD);
	}

	public static boolean equals(CardType val1, CardType val2) {
		if (val1 == null || val1.getCardType() == null) {
			return false;
		}
		return val1.getCardType().equals(val2.getCardType());
	}
}
