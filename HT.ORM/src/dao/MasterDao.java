package dao;

import java.util.HashMap;
import java.util.Map;

import common.Dao;

public class MasterDao {
	private static MasterDao instance = null;
	private Map<Class<?>, Dao<?>> flyweight = null;

	public static void initialize() {
		instance = new MasterDao();
		instance.flyweight = new HashMap<Class<?>, Dao<?>>();
		instance.flyweight.put(GroupDao.class, FactoryDao.getDao(GroupDao.class));
		instance.flyweight.put(CardDao.class, FactoryDao.getDao(CardDao.class));
		instance.flyweight.put(CardStepDao.class, FactoryDao.getDao(CardStepDao.class));
		instance.flyweight.put(StateDao.class, FactoryDao.getDao(StateDao.class));
	}

	@SuppressWarnings("unchecked")
	public static <T> T getDao(Class<T> clz) {
		try {
			if (instance == null) {
				initialize();
			}
			return (T) instance.flyweight.get(clz);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
