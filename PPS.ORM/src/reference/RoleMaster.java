package reference;

import common.FactoryDao;
import dao.RoleDao;
import model.Role;

public class RoleMaster {
	public static final String COMPANY_CHANGE = "CMCH";
	public static final String GROUP_CHANGE = "GRCH";

	public static RoleDao getDao() {
		return FactoryDao.getDao(RoleDao.class);
	}

	public static Role getCompanyChange() {
		return getDao().getRole(COMPANY_CHANGE);
	}

	public static Role getGroupChange() {
		return getDao().getRole(GROUP_CHANGE);
	}

	public static boolean equals(Role val1, Role val2) {
		if (val1 == null || val1.getRole() == null) {
			return false;
		}
		return val1.getRole().equals(val2.getRole());
	}
}
