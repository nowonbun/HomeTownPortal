package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.Group;

public class GroupDao extends Dao<Group> {

	private static List<Group> singleton = null; 
	
	protected GroupDao() {
		super(Group.class);
	}
	
	@SuppressWarnings("unchecked")
	public Group getGroup(String code) {
		if(singleton == null) {
			singleton = Manager.transaction(() -> {
				try {
					Query query = Manager.get().createNamedQuery("Group.findAll", GroupDao.class);
					return (List<Group>) query.getResultList();
				} catch (NoResultException e) {
					return null;
				}
			});
		}
		return singleton.stream().filter(x -> x.getCode().equals(code)).findFirst().get();
	}

}
