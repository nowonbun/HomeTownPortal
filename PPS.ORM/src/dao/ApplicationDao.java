package dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.Application;
import model.ApplicationPK;

public class ApplicationDao extends Dao<Application> {

	protected ApplicationDao() {
		super(Application.class);
	}

	public Application getEntity(ApplicationPK pk) {
		return getEntity(pk.getIdx(), pk.getId());
	}

	public Application getEntity(int idx, String id) {
		return Manager.transaction(() -> {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT a FROM Application a ");
				sb.append(" WHERE a.id.idx = :idx ");
				sb.append(" AND a.id.id = :id ");
				Query query = Manager.get().createQuery(sb.toString());
				query.setParameter("idx", idx);
				query.setParameter("id", id);
				return (Application) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public Application getEntiryApplyingRecently(String id) {
		return Manager.transaction(() -> {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT a FROM Application a ");
				sb.append(" WHERE a.id.id = :id ");
				sb.append(" AND a.stateInfo.isDelete = false ");
				sb.append(" AND a.stateInfo.state.state = 200 ");
				sb.append(" ORDER BY a.id.idx DESC ");
				Query query = Manager.get().createQuery(sb.toString());
				query.setMaxResults(1);
				query.setParameter("id", id);
				return (Application) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}