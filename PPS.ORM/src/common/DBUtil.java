package common;

import java.util.ArrayList;
import java.util.List;

public class DBUtil {
	public static <R, T> List<T> getLazyData(R obj, LambdaExpression<R, List<T>> func) {
		List<T> ret = new ArrayList<>();
		for (T entity : func.run(obj)) {
			ret.add(entity);
		}
		return ret;
	}
}
