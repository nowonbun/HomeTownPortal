package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.User;

public class UserDao extends Dao<User> {

	protected UserDao() {
		super(User.class);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return Manager.transaction(() -> {
			return Manager.get().createNamedQuery("User.findAll").getResultList();
		});
	}

	public User getUser(String id) {
		return Manager.transaction(() -> {
			try {
				String qy = "SELECT u FROM User u WHERE u.id = :id and u.stateInfo.isDelete = false";
				Query query = Manager.get().createQuery(qy);
				query.setParameter("id", id);
				return (User) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
