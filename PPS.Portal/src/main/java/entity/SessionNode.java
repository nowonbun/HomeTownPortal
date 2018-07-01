package entity;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

public class SessionNode {
	private Session socketSession;
	private HttpSession webSession;
	private Class<?> sessionClz;

	public Session getSocketSession() {
		return socketSession;
	}

	public void setSocketSession(Session socketSession) {
		this.socketSession = socketSession;
	}

	public HttpSession getWebSession() {
		return webSession;
	}

	public void setWebSession(HttpSession webSession) {
		this.webSession = webSession;
	}

	public Class<?> getSessionClz() {
		return sessionClz;
	}

	public void setSessionClz(Class<?> sessionClz) {
		this.sessionClz = sessionClz;
	}
}
