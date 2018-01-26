package dao;

import java.util.HashMap;
import java.util.Map;

import common.Dao;
import model.UserInfo;

public final class FactoryDao {

	private static Map<Class<?>, Dao<?>> flyweight = null;

	private static void Init() {
		if (flyweight == null) {
			flyweight = new HashMap<Class<?>, Dao<?>>();
		}
	}

	public static UserInfoDao getUserInfoDao() {
		Init();
		if (!flyweight.containsKey(UserInfo.class)) {
			flyweight.put(UserInfo.class, new UserInfoDao());
		}
		return (UserInfoDao) flyweight.get(UserInfo.class);
	}
}
