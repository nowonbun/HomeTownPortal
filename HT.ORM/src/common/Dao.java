package common;

import java.io.Serializable;

public abstract class Dao<T extends Serializable> {
	private Class<T> clazz;

	protected Dao(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected final void setClass(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected final Class<T> getClazz() {
		return clazz;
	}

	public T findOne(Object id) {
		return Manager.transaction(() -> {
			return Manager.get().find(clazz, id);
		});
	}

	public void create(T entity) {
		Manager.transaction(() -> {
			Manager.get().persist(entity);
		});
	}

	public T update(T entity) {
		return Manager.transaction(() -> {
			return Manager.get().merge(entity);
		});
	}

	public void delete(T entity) {
		Manager.transaction(() -> {
			Manager.get().remove(entity);
		});
	}
}
