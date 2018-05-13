package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.Manager;
import common.MasterDao;
import model.Role;

public class RoleDao extends MasterDao<Role> {

	protected RoleDao() {
		super(Role.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Role> getDataList() {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get().createNamedQuery("Role.findAll", Role.class);
				return (List<Role>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public Role getRole(String role) {
		return getData().stream().filter(x -> x.getName().equals(role)).findFirst().get();
	}

}