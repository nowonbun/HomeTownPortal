package common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import common.interfaces.LambdaExpression;

public class Util {
	private static DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String createCookieKey() {
		String key = UUID.randomUUID().toString();
		return key.replace("-", "") + dateFormat2.format(new Date());
	}

	public static int getCookieExpire() {
		return 60 * 60 * 24 * 365;
	}

	public static String getCookiePath() {
		return PropertyMap.getInstance().getProperty("config", "cookie_path");
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

	public static boolean StringEquals(String val1, String val2) {
		if (val1 == null) {
			return false;
		}
		if (val2 == null) {
			return false;
		}
		return val1.equals(val2);
	}

	public static boolean StringIsEmptyOrNull(String val) {
		if (val == null) {
			return true;
		}
		if (val.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean JsonIsKey(JsonObject jsonobj, String key) {
		return jsonobj.keySet().contains(key) && !jsonobj.isNull(key);
	}

	public static String JsonString(JsonObject jsonobj, String key) {
		if (JsonIsKey(jsonobj, key)) {
			return jsonobj.getString(key);
		}
		return null;
	}

	public static Boolean JsonBoolean(JsonObject jsonobj, String key) {
		if (JsonIsKey(jsonobj, key)) {
			return jsonobj.getBoolean(key);
		}
		return null;
	}

	public static byte[] JsonBytes(JsonObject jsonobj, String key) {
		String ret = JsonString(jsonobj, key);
		if (ret == null) {
			return null;
		}
		return ret.getBytes();
	}

	public static Integer JsonInteger(JsonObject jsonobj, String key) {
		if (JsonIsKey(jsonobj, key)) {
			return jsonobj.getInt(key);
		}
		return null;
	}

	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@Deprecated
	public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		LoggerManager.getLogger(Util.class).debug("package path - " + path);
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<>();
		while (resources.hasMoreElements()) {
			URL resource = (URL) resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		List<Class<?>> classes = new ArrayList<>();
		for (File directory : dirs) {
			LoggerManager.getLogger(Util.class).debug("directory - " + directory.getName());
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(
						Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	public static String convertMD5(String val) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(val.getBytes(StandardCharsets.UTF_8));
			return DatatypeConverter.printHexBinary(bytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
