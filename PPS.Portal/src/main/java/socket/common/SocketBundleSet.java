package socket.common;

import java.util.ArrayList;
import java.util.List;

public class SocketBundleSet {
	private static SocketBundleSet singleton = null;
	private List<Class<?>> list = new ArrayList<>();

	public static void setClass(Class<?> clz) {
		if (singleton == null) {
			singleton = new SocketBundleSet();
		}
		singleton.list.add(clz);
	}

	public static List<Class<?>> getList() {
		if (singleton == null) {
			singleton = new SocketBundleSet();
		}
		return singleton.list;
	}

	private SocketBundleSet() {

	}
}
