package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import entity.SessionNode;

public class SessionFactory {
	private Map<Class<?>, List<SessionNode>> sessionUsers = Collections.synchronizedMap(new HashMap<>());
	private static SessionFactory instance = null;

	private SessionFactory() {
	}

	public static List<SessionNode> getSessionList(Class<?> clz) {
		if (instance == null) {
			instance = new SessionFactory();
		}
		if (!instance.sessionUsers.containsKey(clz)) {
			instance.sessionUsers.put(clz, new ArrayList<>());
		}
		return instance.sessionUsers.get(clz);
	}

	public static SessionNode addSession(Class<?> clz, Session socketSession, HttpSession webSession) {
		SessionNode node = createSessionNode(clz, socketSession, webSession);
		getSessionList(clz).add(node);
		return node;
	}

	public static SessionNode removeSession(Class<?> clz, Session session) {
		SessionNode node = getSession(clz, session);
		if (node != null) {
			getSessionList(clz).remove(node);
		}
		return node;
	}

	public static SessionNode getSession(Class<?> clz, Session session) {
		return getSessionList(clz).stream().filter(s -> {
			if (s.getSocketSession() == session) {
				return true;
			}
			return false;
		}).findFirst().get();
	}

	public static SessionNode createSessionNode(Class<?> sessionClz, Session socketSession, HttpSession webSession) {
		SessionNode node = new SessionNode();
		node.setSocketSession(socketSession);
		node.setWebSession(webSession);
		node.setSessionClz(sessionClz);
		return node;
	}
}
