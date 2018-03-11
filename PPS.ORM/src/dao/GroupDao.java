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
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		if (singleton == null) {
			singleton = Manager.transaction(() -> {
				try {
					Query query = Manager.get().createNamedQuery("Group.findAll", Group.class);
					return (List<Group>) query.getResultList();
				} catch (NoResultException e) {
					return null;
				}
			});
		}
	}

	public Group getGroup(String code) {
		initialize();
		return singleton.stream().filter(x -> x.getCode().equals(code)).findFirst().get();
	}

}
