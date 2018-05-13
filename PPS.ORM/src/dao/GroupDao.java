package dao;

import common.FactoryDao;
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
}
