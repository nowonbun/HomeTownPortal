package common;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import authentication.UserService;
import entity.SessionNode;

public abstract class ICommon {
	protected HttpSession getSession(SessionNode session) {
		return session.getWebSession();
	}

	protected HttpSession getSession(Class<?> sessionClz, Session socketSession) {
		return SessionFactory.getSession(sessionClz, socketSession).getWebSession();
	}

	protected UserService getUserinfo(SessionNode session) {
		return getUserinfo(session.getSessionClz(), session.getSocketSession());
	}

	protected UserService getUserinfo(Class<?> sessionClz, Session socketSession) {
		return (UserService) getSession(sessionClz, socketSession).getAttribute(UserService.SESSION_ID);
	}

	protected boolean isAuth(SessionNode session) {
		return isAuth(session.getSessionClz(), session.getSocketSession());
	}

	protected boolean isAuth(Class<?> sessionClz, Session socketSession) {
		if (getSession(sessionClz, socketSession) == null) {
			return false;
		}
		return getSession(sessionClz, socketSession).getAttribute(UserService.SESSION_ID) != null;
	}
}
