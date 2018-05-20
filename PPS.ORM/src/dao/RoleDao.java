package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.Manager;
import common.MasterDao;
import model.Role;
import model.User;

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
		return getData().stream().filter(x -> x.getRole().equals(role)).findFirst().get();
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> getRolebyUser(User user) {
		return Manager.transaction(() -> {
			try {
				/* 
				 *  SELECT a.ROLE, b.COMPANY_ID, c.GROUP_ID, d.USER_ID FROM MST_ROLE a
 				 *  LEFT OUTER JOIN (SELECT x.* FROM MAP_ACTION_ROLE_COMPANY x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) b ON a.ROLE = b.ROLE_CODE
 				 *  LEFT OUTER JOIN (SELECT x.* FROM MAP_ACTION_ROLE_GROUP x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) c ON a.ROLE = c.ROLE_CODE
 				 *  LEFT OUTER JOIN (SELECT x.* FROM MAP_ACTION_ROLE_USER x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) d ON a.ROLE = d.ROLE_CODE;
				 */
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT a.ROLE, b.COMPANY_ID, c.GROUP_ID, d.USER_ID FROM ");
				sb.append(" MST_ROLE a ");
				sb.append(" LEFT OUTER JOIN (SELECT x.* FROM MAP_ACTION_ROLE_COMPANY x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) b ");
				sb.append(" ON a.ROLE = b.ROLE_CODE ");
				sb.append(" LEFT OUTER JOIN (SELECT x.* FROM MAP_ACTION_ROLE_GROUP x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) c ");
				sb.append(" ON a.ROLE = c.ROLE_CODE ");
				sb.append(" LEFT OUTER JOIN (SELECT x.* FROM MAP_ACTION_ROLE_USER x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) d ");
				sb.append(" ON a.ROLE = d.ROLE_CODE ");
				Query query = Manager.get().createNativeQuery(sb.toString());
				List<Object[]> roles = query.getResultList();
				List<Role> ret = new ArrayList<>();
				for (Object[] r : roles) {
					if (r[1] != null && (int) r[1] != user.getCompany().getId()) {
						continue;
					}
					if (r[2] != null && (int) r[2] != user.getGroup().getId()) {
						continue;
					}
					if (r[3] != null && user.getId().equals(r[3].toString())) {
						continue;
					}
					ret.add(getRole(r[0].toString()));
				}
				return ret;
			} catch (NoResultException e) {
				return null;
			}
		});
	}

}