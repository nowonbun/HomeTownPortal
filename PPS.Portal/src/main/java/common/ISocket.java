package common;

import java.io.StringReader;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import entity.WebSocketNode;
import socket.SessionFactory;

public class ISocket {
	@OnOpen
	public void handleOpen(Session userSession) {
		SessionFactory.addSession(this.getClass(), userSession);
		System.out.println("connection");
	}

	@OnClose
	public void handleClose(Session userSession) {
		SessionFactory.removeSession(this.getClass(), userSession);
		System.out.println("disconnection");
	}

	public String createJson(String key, String data) {
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

	public WebSocketNode createNode(String json) {
		try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {
			JsonObject object = jsonReader.readObject();
			WebSocketNode node = new WebSocketNode();
			node.setId(object.getString("id"));
			node.setUrl(object.getString("url"));
			node.setData(object.getString("data"));
			return node;
		}
	}
}
