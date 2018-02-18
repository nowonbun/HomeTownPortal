package HT.Portal.Socket;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import HT.Portal.common.ISocket;

@ServerEndpoint("/index")
public class Index extends ISocket {

	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException {
		System.out.println("message - " + message);
		//WebSocketNode node = createNode(message);
		userSession.getBasicRemote().sendText(createJson("test", "data"));
	}
}
