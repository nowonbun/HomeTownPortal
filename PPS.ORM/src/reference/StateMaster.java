package reference;

import common.FactoryDao;
import dao.StateDao;
import model.State;

public final class StateMaster {

	public static final int GOOGLE_ID = 100;
	public static final int PRIVATE_ID = 101;
	public static final int APPLYING = 200;
	public static final int APPLICATION_REFUSED = 201;
	public static final int APPLICATION_APPROVED = 202;
	public static final int DEFAULT_TRANSACTION_DATA = 999;

	public static StateDao getDao() {
		return FactoryDao.getDao(StateDao.class);
	}

	public static State getGoogleId() {
		return getDao().getState(GOOGLE_ID);
	}

	public static State getPrivateId() {
		return getDao().getState(PRIVATE_ID);
	}

	public static State getApplying() {
		return getDao().getState(APPLYING);
	}

	public static State getApplicationRefused() {
		return getDao().getState(APPLICATION_REFUSED);
	}

	public static State getApplicationApproved() {
		return getDao().getState(APPLICATION_APPROVED);
	}

	public static State getDefaultTransactionData() {
		return getDao().getState(DEFAULT_TRANSACTION_DATA);
	}

	public static boolean equals(State val1, State val2) {
		if (val1 == null) {
			return false;
		}
		if (val2 == null) {
			return false;
		}
		return val1.getState() == val2.getState();
	}
}
