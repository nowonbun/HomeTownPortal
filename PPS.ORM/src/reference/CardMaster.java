package reference;

import common.FactoryDao;
import dao.CardDao;
import model.Card;

public class CardMaster {
	public static final String ADMIN = "ADMN";
	public static final String ACTION_ROLE = "ATRL";
	public static final String CARD_MASTER_SETTING = "CMST";
	public static final String DATA_MASTER_SETTING = "MSST";
	public static final String PROFILE = "PRFL";
	public static final String USER_MANAGEMENT = "USMN";
	public static final String VIEW_ROLE = "VWRL";
	public static final String COMPANY_N_GROUP_SETTING = "CGST";

	public static CardDao getDao() {
		return FactoryDao.getDao(CardDao.class);
	}

	public static Card getAdminCard() {
		return getDao().getCard(ADMIN);
	}

	public static Card getActionRoleCard() {
		return getDao().getCard(ACTION_ROLE);
	}

	public static Card getCardMasterSettingCard() {
		return getDao().getCard(CARD_MASTER_SETTING);
	}

	public static Card getDataMasterSettingCard() {
		return getDao().getCard(DATA_MASTER_SETTING);
	}

	public static Card getProfileCard() {
		return getDao().getCard(PROFILE);
	}

	public static Card getUserManagementCard() {
		return getDao().getCard(USER_MANAGEMENT);
	}

	public static Card getViewRoleCard() {
		return getDao().getCard(VIEW_ROLE);
	}

	public static Card getCompanyNGroupSettingCard() {
		return getDao().getCard(COMPANY_N_GROUP_SETTING);
	}

	public static boolean equals(Card val1, Card val2) {
		if (val1 == null || val1.getCode() == null) {
			return false;
		}
		return val1.getCode().equals(val2.getCode());
	}
}
