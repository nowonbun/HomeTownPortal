package common;

import java.io.Serializable;

public abstract class TransactionDao<T extends Serializable> extends Dao<T> {

	protected TransactionDao(Class<T> clazz) {
		super(clazz);
	}

}
