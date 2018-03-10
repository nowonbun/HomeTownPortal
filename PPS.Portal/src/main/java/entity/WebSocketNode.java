package entity;

public class WebSocketNode {
	private String key;
	private String data;
	private SessionNode session;

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

	public SessionNode getSession() {
		return session;
	}

	public void setSession(SessionNode session) {
		this.session = session;
	}
}
