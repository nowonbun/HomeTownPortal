package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.Userinfo;

public class UserinfoDao extends Dao<Userinfo> {

	protected UserinfoDao() {
		super(Userinfo.class);
	}

	@SuppressWarnings("unchecked")
	public List<Userinfo> findAll() {
		return Manager.transaction(() -> {
			return Manager.get().createNamedQuery("Userinfo.findAll").getResultList();
		});
	}

	public Userinfo getUser(String id) {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get().createQuery("SELECT u FROM Userinfo u WHERE u.id = :id");
				query.setParameter("id", id);
				return (Userinfo) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
