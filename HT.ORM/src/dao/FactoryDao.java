package dao;

import java.util.HashMap;
import java.util.Map;

import common.Dao;
import model.Cookieinfo;
import model.Userinfo;

public final class FactoryDao {

	private static Map<Class<?>, Dao<?>> flyweight = null;

	private static void Init() {
		if (flyweight == null) {
			flyweight = new HashMap<Class<?>, Dao<?>>();
		}
	}

	public static UserinfoDao getUserInfoDao() {
		Init();
		if (!flyweight.containsKey(Userinfo.class)) {
			flyweight.put(Userinfo.class, new UserinfoDao());
		}
		return (UserinfoDao) flyweight.get(Userinfo.class);
	}

	public static CookieinfoDao getCookieinfoDao() {
		Init();
		if (!flyweight.containsKey(Cookieinfo.class)) {
			flyweight.put(Cookieinfo.class, new CookieinfoDao());
		}
		return (CookieinfoDao) flyweight.get(Cookieinfo.class);
	}
}
