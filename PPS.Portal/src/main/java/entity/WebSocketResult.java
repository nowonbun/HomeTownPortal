package entity;

import common.WebSocketResultType;

public class WebSocketResult {
	private WebSocketResultType type;
	private String key;
	private String data;
	public WebSocketResultType getType() {
		return type;
	}
	public void setType(WebSocketResultType type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
