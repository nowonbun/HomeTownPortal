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

import entity.SessionNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

public abstract class ISocket extends ICommon {

	public static String key = "key";
	public static String data = "data";

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
		main(node);
	}

	protected void sendMessage(String key, String data) throws IOException {
		String json = createJson(key, data);
		sendMessage(json);
	}

	protected void sendMessage(String key, String data, SessionNode socketSession) throws IOException {
		String json = createJson(key, data);
		sendMessage(json, socketSession.getSocketSession());
	}

	protected void sendMessage(WebSocketResult node) throws IOException {
		String json = createJson(node.getKey(), node.getData());
		if (node.getType() == WebSocketResultType.Broadcast) {
			sendMessage(json);
		} else {
			sendMessage(json, node.getNode().getSession().getSocketSession());
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

	protected abstract void main(WebSocketNode node);

	protected String createJson(String key, String data) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add(ISocket.key, key);
		builder.add(ISocket.data, data);
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
			node.setKey(object.getString(ISocket.key));
			if (node.getKey() == null) {
				return null;
			}
			node.setData(object.getString(ISocket.data));
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

	protected String getClassName(Class<?> clz) {
		return clz.getAnnotation(Workflow.class).name();
	}
}
