package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.Group;
import model.User;

public class GroupDao extends Dao<Group> {

	protected GroupDao() {
		super(Group.class);
	}
}
