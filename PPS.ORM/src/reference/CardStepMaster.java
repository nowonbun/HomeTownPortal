package reference;

import common.FactoryDao;
import dao.CardStepDao;
import model.CardStep;

public class CardStepMaster {

	public static String ADMIN = "ADMN";
	public static String HOME = "HOME";
	public static String MASTER = "MAST";
	public static String MODAL = "MODL";

	public static CardStepDao getDao() {
		return FactoryDao.getDao(CardStepDao.class);
	}

	public static CardStep getAdminStep() {
		return getDao().getCardStep(ADMIN);
	}

	public static CardStep getHomeStep() {
		return getDao().getCardStep(HOME);
	}

	public static CardStep getMasterStep() {
		return getDao().getCardStep(MASTER);
	}

	public static CardStep getModalStep() {
		return getDao().getCardStep(MODAL);
	}

	public static boolean equals(CardStep val1, CardStep val2) {
		if (val1 == null || val1.getStep() == null) {
			return false;
		}
		return val1.getStep().equals(val2.getStep());
	}
}
