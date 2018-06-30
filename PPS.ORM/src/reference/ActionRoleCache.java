package reference;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import common.FactoryDao;
import common.Permission;
import dao.RoleDao;
import model.Role;
import model.User;

public class ActionRoleCache {
	private List<Permission> permissionlist = null;

	private ActionRoleCache() {
		this.permissionlist = FactoryDao.getDao(RoleDao.class).getPermission();
	}

	private static ActionRoleCache singleton = null;

	private static ActionRoleCache getinstance() {
		if (singleton == null) {
			reset();
		}
		return singleton;
	}

	public static void reset() {
		singleton = new ActionRoleCache();
	}

	public static List<Role> getRoleByUser(User user) {
		if (user == null) {
			return null;
		}
		List<Role> ret = new ArrayList<>();
		for (Permission permission : getinstance().permissionlist) {
			if (permission.getCompanyid() != null && permission.getCompanyid() != user.getCompany().getId()) {
				continue;
			}
			if (permission.getGroupid() != null && permission.getGroupid() != user.getGroup().getId()) {
				continue;
			}
			if (permission.getUserid() != null && user.getId().equals(permission.getUserid())) {
				continue;
			}
			ret.add(RoleMaster.getDao().getRole(permission.getCode()));
		}
		return ret;
	}

	public static boolean hasPermission(User user, Role role) {
		try {
			if (user == null || role == null) {
				return false;
			}
			return getRoleByUser(user).stream().filter(x -> RoleMaster.equals(x, role)).findFirst().isPresent();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
