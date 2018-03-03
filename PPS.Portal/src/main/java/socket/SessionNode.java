package socket;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

public class SessionNode {
	private Session socketSession;
	private HttpSession webSession;
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
}
