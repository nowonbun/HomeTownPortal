package HT.Portal.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import HT.Portal.common.interfaces.LambdaExpression;

public class Util {
	private static DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String createCookieKey() {
		String key = UUID.randomUUID().toString();
		return key.replace("-", "") + dateFormat2.format(new Date());
	}

	public static int getCookieExpire() {
		return 60 * 60 * 24 * 365;
	}

	public static String getCookiePath() {
		return "/HT.Portal/";
	}

	public static <T> T searchArray(T[] array, LambdaExpression<T, Boolean> condition) {
		if (array == null) {
			return null;
		}
		for (T node : array) {
			if (condition.run(node)) {
				return node;
			}
		}
		return null;
	}
}
