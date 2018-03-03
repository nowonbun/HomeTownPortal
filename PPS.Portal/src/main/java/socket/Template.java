package socket;

import javax.websocket.server.ServerEndpoint;

import common.HttpSessionConfigurator;
import common.ISocket;
import entity.WebSocketNode;
import entity.WebSocketResult;

@ServerEndpoint(value = "/template", configurator = HttpSessionConfigurator.class)
public class Template extends ISocket {

	public WebSocketResult main(WebSocketNode node) {
		if (!isAuth(node.getSession())) {
			return createWebSocketResult("login", "NG");
		}
		return createWebSocketResult("index", "TEST");
	}
}
