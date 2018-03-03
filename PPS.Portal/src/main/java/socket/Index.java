package socket;

import javax.websocket.server.ServerEndpoint;

import common.HttpSessionConfigurator;
import common.ISocket;
import entity.WebSocketNode;
import entity.WebSocketResult;

@ServerEndpoint(value = "/index", configurator = HttpSessionConfigurator.class)
public class Index extends ISocket {

	public WebSocketResult main(WebSocketNode node) {
		if(!isAuth(node.getSession())) {
			return createWebSocketResult("login", "NG");
		}
		return createWebSocketResult("index", "TEST");
	}
}
