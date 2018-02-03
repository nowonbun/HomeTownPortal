package dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.Cookieinfo;
import model.CookieinfoPK;

public class CookieinfoDao extends Dao<Cookieinfo> {

	protected CookieinfoDao() {
		super(Cookieinfo.class);
	}

	public Cookieinfo getEntity(CookieinfoPK pk) {
		return getEntity(pk.getId(), pk.getCookiekey());
	}

	public Cookieinfo getEntity(String id, String cookiekey) {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get()
						.createQuery("SELECT c FROM Cookieinfo c WHERE c.id.id = :id and c.id.cookiekey = :cookiekey");
				query.setParameter("id", id);
				query.setParameter("cookiekey", cookiekey);
				return (Cookieinfo) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
	
	public Cookieinfo getEntityByCookiekey(String cookiekey) {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get()
						.createQuery("SELECT c FROM Cookieinfo c WHERE c.id.cookiekey = :cookiekey");
				query.setParameter("cookiekey", cookiekey);
				return (Cookieinfo) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
