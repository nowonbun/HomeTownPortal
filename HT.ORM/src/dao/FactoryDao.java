package dao;

import java.util.HashMap;
import java.util.Map;

import common.Dao;

public class FactoryDao {

	private static FactoryDao instance = null;
	private static Map<Class<?>, Dao<?>> flyweight = null;

	@SuppressWarnings("unchecked")
	public static <T> T getDao(Class<T> clz) {
		try {
			if (instance == null) {
				instance = new FactoryDao();
			}
			if (flyweight == null) {
				flyweight = new HashMap<Class<?>, Dao<?>>();
			}
			if (!flyweight.containsKey(clz)) {
				flyweight.put(clz, (Dao<?>) clz.newInstance());
			}
			return (T) flyweight.get(clz);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
