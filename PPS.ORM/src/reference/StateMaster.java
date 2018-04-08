package reference;

import dao.MasterDao;
import dao.StateDao;
import model.State;

public final class StateMaster {

	public static int GOOGLE_ID = 100;
	public static int PRIVATE_ID = 101;
	public static int APPLYING = 200;
	public static int APPLICATION_REFUSED = 201;
	public static int APPLICATION_APPROVED = 202;

	public static StateDao getDao() {
		return MasterDao.getDao(StateDao.class);
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
}
