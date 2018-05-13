package common;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MasterDao<T extends Serializable> extends Dao<T> {

	private List<T> flyweight = null;

	protected MasterDao(Class<T> clazz) {
		super(clazz);
		initialize();
	}

	protected abstract List<T> getDataList();

	public MasterDao<T> initialize() {
		if (flyweight == null) {
			reset();
		}
		return this;
	}

	public MasterDao<T> reset() {
		flyweight = getDataList();
		return this;
	}

	public List<T> getData() {
		initialize();
		return flyweight.stream().collect(Collectors.toList());
	}
}
