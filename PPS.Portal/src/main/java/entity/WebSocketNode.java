package entity;

public class WebSocketNode {
	private String control;
	private String action;
	private String data;
	private SessionNode session;

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
