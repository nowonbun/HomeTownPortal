package dao;

import java.util.List;

import common.Dao;
import common.Manager;
import model.UserInfo;

public class UserInfoDao extends Dao<UserInfo> {

	protected UserInfoDao() {
		super(UserInfo.class);
	}

	@SuppressWarnings("unchecked")
	public List<UserInfo> findAll() {
		return Manager.transaction(() -> {
			return Manager.get().createNamedQuery("UserInfo.findAll").getResultList();
		});
	}
}
