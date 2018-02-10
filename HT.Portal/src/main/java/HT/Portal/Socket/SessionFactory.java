package HT.Portal.Socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

public class SessionFactory {
	private Map<Class<?>, List<Session>> sessionUsers = Collections.synchronizedMap(new HashMap<>());
	private static SessionFactory instance = null;

	private SessionFactory() {
	}

	public static List<Session> getSessionList(Class<?> clz) {
		if (instance == null) {
			instance = new SessionFactory();
		}
		if (!instance.sessionUsers.containsKey(clz)) {
			instance.sessionUsers.put(clz, new ArrayList<>());
		}
		return instance.sessionUsers.get(clz);
	}

	public static Session addSession(Class<?> clz, Session session) {
		getSessionList(clz).add(session);
		return session;
	}

	public static Session removeSession(Class<?> clz, Session session) {
		getSessionList(clz).remove(session);
		return session;
	}
}
