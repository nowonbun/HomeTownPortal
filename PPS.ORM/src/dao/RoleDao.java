package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.Role;

public class RoleDao extends Dao<Role> {

	private static List<Role> singleton = null;

	protected RoleDao() {
		super(Role.class);
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		if (singleton == null) {
			singleton = Manager.transaction(() -> {
				try {
					Query query = Manager.get().createNamedQuery("Role.findAll", Role.class);
					return (List<Role>) query.getResultList();
				} catch (NoResultException e) {
					return null;
				}
			});
		}
	}

	public Role getCardType(String role) {
		initialize();
		return singleton.stream().filter(x -> x.getName().equals(role)).findFirst().get();
	}

}