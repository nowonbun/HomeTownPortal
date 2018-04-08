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
				Query query = Manager.get()
						.createQuery("SELECT a FROM Application a WHERE a.id.idx = :idx and a.id.id = :id");
				query.setParameter("idx", idx);
				query.setParameter("id", id);
				return (Application) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}