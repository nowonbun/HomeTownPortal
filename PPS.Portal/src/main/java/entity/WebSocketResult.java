package entity;

import java.util.ArrayList;
import java.util.List;

import common.WebSocketResultType;

public class WebSocketResult {
	private List<NavigateNode> navigate = null;
	private WebSocketNode node;
	private WebSocketResultType type;
	private String control;
	private String action;
	private String data;

	public WebSocketResult() {

	}

	public WebSocketResultType getType() {
		return type;
	}

	public void setType(WebSocketResultType type) {
		this.type = type;
	}

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

	public WebSocketNode getNode() {
		return node;
	}

	public void setNode(WebSocketNode node) {
		this.node = node;
	}

	public void addNavigate(NavigateNode navi) {
		navigate.add(navi);
	}

	public void addRangeNavigate(NavigateNode... navi) {
		if (navigate == null) {
			navigate = new ArrayList<>();
			navigate.add(new NavigateNode(false, "./#!/", "Home", "main", "./views/main.tpl.jsp"));
		}
		for (NavigateNode n : navi) {
			navigate.add(n);
		}
	}

	public void addRangeNavigate(List<NavigateNode> navi) {
		navigate.addAll(navi);
	}

	public List<NavigateNode> getNavigate() {
		return navigate;
	}
}
