package socket;

import javax.websocket.server.ServerEndpoint;

import common.ISocket;
import entity.WebSocketNode;
import entity.WebSocketResult;

@ServerEndpoint("/login")
public class Login extends ISocket {

	public WebSocketResult main(WebSocketNode node) {
		if(isAuth(node.getSession())) {
			return createWebSocketResult("login", "OK");
		}
		return createWebSocketResult("login", "NG");
	}
}