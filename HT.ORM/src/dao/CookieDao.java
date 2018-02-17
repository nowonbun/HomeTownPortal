package dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.Cookie;
import model.CookiePK;

public class CookieDao extends Dao<Cookie> {

	protected CookieDao() {
		super(Cookie.class);
	}

	public Cookie getEntity(CookiePK pk) {
		return getEntity(pk.getId(), pk.getCookiekey());
	}

	public Cookie getEntity(String id, String cookiekey) {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get()
						.createQuery("SELECT c FROM Cookie c WHERE c.id.id = :id and c.id.cookiekey = :cookiekey");
				query.setParameter("id", id);
				query.setParameter("cookiekey", cookiekey);
				return (Cookie) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public Cookie getEntityByCookiekey(String cookiekey) {
		return Manager.transaction(() -> {
			try {
				String qy = "SELECT c FROM Cookie c WHERE c.id.cookiekey = :cookiekey and c.stateInfo.isDelete = false";
				Query query = Manager.get().createQuery(qy);
				query.setParameter("cookiekey", cookiekey);
				return (Cookie) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
