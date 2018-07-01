package common;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import dao.CardDao;
import dao.CardStepDao;
import dao.CardTypeDao;
import dao.LookUpDao;
import dao.RoleDao;
import dao.StateDao;

public class FactoryDao {

	private static FactoryDao instance = null;
	private Map<Class<?>, Dao<?>> flyweight = null;

	@SuppressWarnings("unchecked")
	public static <T> T getDao(Class<T> clz) {
		try {
			if (instance == null) {
				instance = new FactoryDao();
			}
			if (instance.flyweight == null) {
				instance.flyweight = new HashMap<Class<?>, Dao<?>>();
			}
			if (!instance.flyweight.containsKey(clz)) {
				Constructor<T> constructor = clz.getDeclaredConstructor();
				constructor.setAccessible(true);
				instance.flyweight.put(clz, (Dao<?>) constructor.newInstance());
			}
			return (T) instance.flyweight.get(clz);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static void initializeMaster() {
		FactoryDao.getDao(CardDao.class);
		FactoryDao.getDao(CardStepDao.class);
		FactoryDao.getDao(StateDao.class);
		FactoryDao.getDao(CardTypeDao.class);
		FactoryDao.getDao(RoleDao.class);
		FactoryDao.getDao(LookUpDao.class);
	}

	public static void resetMaster() {
		FactoryDao.getDao(CardDao.class).reset();
		FactoryDao.getDao(CardStepDao.class).reset();
		FactoryDao.getDao(StateDao.class).reset();
		FactoryDao.getDao(CardTypeDao.class).reset();
		FactoryDao.getDao(RoleDao.class).reset();
		FactoryDao.getDao(LookUpDao.class).reset();
	}
}
