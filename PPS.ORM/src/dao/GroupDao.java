package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.FactoryDao;
import common.Manager;
import common.TransactionDao;
import model.Group;

public class GroupDao extends TransactionDao<Group> {

	protected GroupDao() {
		super(Group.class);
	}

	public Group getGroup(int id) {
		return super.findOne(id);
	}

	public Group getDefaultGroup() {
		return getGroup(FactoryDao.getDao(LookUpDao.class).getValueInt("DefaultGroup"));
	}

	@SuppressWarnings("unchecked")
	public List<Group> getGroupAll() {
		return Manager.transaction(() -> {
			try {
				String qy = "SELECT g FROM Group g WHERE g.stateInfo.isDelete = false";
				Query query = Manager.get().createQuery(qy);
				return (List<Group>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Group> getGroupByCompany(int companyId){
		return Manager.transaction(() -> {
			try {
				String qy = "SELECT g FROM Group g WHERE g.company.id = :companyId AND g.stateInfo.isDelete = false";
				Query query = Manager.get().createQuery(qy);
				query.setParameter("companyId", companyId);
				return (List<Group>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
