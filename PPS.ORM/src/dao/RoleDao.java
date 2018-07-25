package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.MasterDao;
import common.Permission;
import model.Role;

public class RoleDao extends MasterDao<Role> {

	protected RoleDao() {
		super(Role.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Role> getDataList() {
		return transaction((em) -> {
			try {
				Query query = em.createNamedQuery("Role.findAll", Role.class);
				return (List<Role>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public Role getRole(String role) {
		try {
			return getData().stream().filter(x -> x.getRole().equals(role)).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public List<Role> getRoleAll() {
		return getData();
	}
	
	public void deleteRole(String code) {
		transaction((em) -> {
			Query query = em.createNativeQuery(" DELETE FROM MAP_ACTION_ROLE_COMPANY WHERE ROLE_CODE = ?");
			query.setParameter(1, code);
			query.executeUpdate();

			query = em.createNativeQuery(" DELETE FROM MAP_ACTION_ROLE_GROUP WHERE ROLE_CODE = ?");
			query.setParameter(1, code);
			query.executeUpdate();

			query = em.createNativeQuery(" DELETE FROM MAP_ACTION_ROLE_USER WHERE ROLE_CODE = ?");
			query.setParameter(1, code);
			query.executeUpdate();
		});
	}

	public void setRole(String code, List<Integer> coms, List<Integer> grps, List<String> usrs) {
		transaction((em) -> {
			Query query = null;
			for (Integer com : coms) {
				query = em.createNativeQuery(" INSERT INTO MAP_ACTION_ROLE_COMPANY (ROLE_CODE, COMPANY_ID) VALUES(?, ?)");
				query.setParameter(1, code);
				query.setParameter(2, com);
				query.executeUpdate();
			}
			for (Integer grp : grps) {
				query = em.createNativeQuery(" INSERT INTO MAP_ACTION_ROLE_GROUP (ROLE_CODE, GROUP_ID) VALUES(?, ?)");
				query.setParameter(1, code);
				query.setParameter(2, grp);
				query.executeUpdate();
			}
			for (String usr : usrs) {
				query = em.createNativeQuery(" INSERT INTO MAP_ACTION_ROLE_USER (ROLE_CODE, USER_ID) VALUES(?, ?)");
				query.setParameter(1, code);
				query.setParameter(2, usr);
				query.executeUpdate();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermission() {
		return transaction((em) -> {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT a.ROLE, b.COMPANY_ID, c.GROUP_ID, d.USER_ID FROM ");
				sb.append(" MST_ROLE a ");
				sb.append(" LEFT OUTER JOIN MAP_ACTION_ROLE_COMPANY b ");
				sb.append(" ON a.ROLE = b.ROLE_CODE ");
				sb.append(" LEFT OUTER JOIN MAP_ACTION_ROLE_GROUP c ");
				sb.append(" ON a.ROLE = c.ROLE_CODE ");
				sb.append(" LEFT OUTER JOIN MAP_ACTION_ROLE_USER d ");
				sb.append(" ON a.ROLE = d.ROLE_CODE ");
				Query query = em.createNativeQuery(sb.toString());
				List<Permission> ret = new ArrayList<>();
				List<Object[]> roles = query.getResultList();
				for (Object[] node : roles) {
					ret.add(new Permission(node[0], node[1], node[2], node[3]));
				}
				return ret;
			} catch (NoResultException e) {
				return null;
			}
		});
	}

}