package common;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.stream.JsonParsingException;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import authentication.UserService;
import entity.WebSocketNode;
import entity.WebSocketResult;
import socket.SessionFactory;
import socket.SessionNode;

public abstract class ISocket {

	protected HttpSession getSession(SessionNode session) {
		return session.getWebSession();
	}

	protected HttpSession getSession(Session socketSession) {
		return SessionFactory.getSession(this.getClass(), socketSession).getWebSession();
	}

	protected UserService getUserinfo(SessionNode session) {
		return getUserinfo(session.getSocketSession());
	}

	protected UserService getUserinfo(Session socketSession) {
		return (UserService) getSession(socketSession).getAttribute(UserService.SESSION_ID);
	}

	protected boolean isAuth(SessionNode session) {
		return isAuth(session.getSocketSession());
	}

	protected boolean isAuth(Session socketSession) {
		if(getSession(socketSession) == null) {
			return false;
		}
		return getSession(socketSession).getAttribute(UserService.SESSION_ID) != null;
	}

	@OnOpen
	public void handleOpen(Session socketSession, EndpointConfig config) {
		HttpSession session = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		SessionFactory.addSession(this.getClass(), socketSession, session);
		System.out.println("connection");
	}

	@OnClose
	public void handleClose(Session socketSession) {
		SessionFactory.removeSession(this.getClass(), socketSession);
		System.out.println("disconnection");
	}

	@OnMessage
	public void handleMessage(String message, Session socketSession) throws IOException {
		WebSocketNode node = createNode(message, socketSession);
		if (node == null) {
			sendMessage(PropertyMap.getInstance().getProperty("message", "socket_error"), socketSession);
			return;
		}
		WebSocketResult ret = main(node);
		String json = createJson(ret.getKey(), ret.getData());
		if (ret.getType() == WebSocketResultType.Broadcast) {
			sendMessage(json);
		} else {
			sendMessage(json, socketSession);
		}
	}

	protected void sendMessage(String message) {
		SessionFactory.getSessionList(this.getClass()).parallelStream().forEach(s -> {
			try {
				s.getSocketSession().getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	protected void sendMessage(String message, Session socketSession) throws IOException {
		socketSession.getBasicRemote().sendText(message);
	}

	protected abstract WebSocketResult main(WebSocketNode node);

	protected String createJson(String key, String data) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("key", key);
		builder.add("data", data);
		JsonObject jsonObject = builder.build();
		StringWriter stringwriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringwriter)) {
			jsonWriter.write(jsonObject);
		}
		return stringwriter.toString();
	}

	protected WebSocketNode createNode(String json, Session socketSession) {
		try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {
			JsonObject object = jsonReader.readObject();
			WebSocketNode node = new WebSocketNode();
			node.setKey(object.getString("key"));
			if (node.getKey() == null) {
				return null;
			}
			node.setData(object.getString("data"));
			if (node.getData() == null) {
				return null;
			}
			node.setSession(SessionFactory.getSession(this.getClass(), socketSession));
			if (node.getSession() == null) {
				return null;
			}
			return node;
		} catch (JsonParsingException e) {
			return null;
		}
	}

	protected WebSocketResult createWebSocketResult(String key, String data) {
		return createWebSocketResult(WebSocketResultType.Single, key, data);
	}

	protected WebSocketResult createWebSocketResult(WebSocketResultType type, String key, String data) {
		WebSocketResult ret = new WebSocketResult();
		ret.setType(type);
		ret.setKey(key);
		ret.setData(data);
		return ret;
	}
}
