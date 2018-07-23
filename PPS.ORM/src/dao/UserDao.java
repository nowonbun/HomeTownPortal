package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.Dao;
import model.User;

public class UserDao extends Dao<User> {

	protected UserDao() {
		super(User.class);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return transaction((em) -> {
			return em.createNamedQuery("User.findAll").getResultList();
		});
	}

	public User getUser(String id) {
		return transaction((em) -> {
			try {
				String qy = "SELECT u FROM User u WHERE u.id = :id and u.stateInfo.isDelete = false";
				Query query = em.createQuery(qy);
				query.setParameter("id", id);
				return (User) query.getSingleResult();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
