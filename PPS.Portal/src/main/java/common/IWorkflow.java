package common;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

public abstract class IWorkflow extends ICommon {
	public static String Init = "init";

	public abstract WebSocketResult init(WebSocketNode node);

	protected abstract NavigateNode[] navigation();

	protected WebSocketResult createWebSocketError(WebSocketNode node) {
		return createWebSocketResult(WebSocketResultType.Single, node, node.getControl(), node.getAction(), createNotification(NotificationType.Danger, "System error"), navigation());
	}

	protected WebSocketResult createWebSocketResult(WebSocketNode node) {
		return createWebSocketResult(WebSocketResultType.Single, node, node.getControl(), node.getAction(), "", navigation());
	}

	protected WebSocketResult createWebSocketResult(String ret, WebSocketNode node) {
		return createWebSocketResult(WebSocketResultType.Single, node, node.getControl(), node.getAction(), ret, navigation());
	}

	protected WebSocketResult createWebSocketResult(WebSocketResultType type, String ret, WebSocketNode node) {
		return createWebSocketResult(type, node, node.getControl(), node.getAction(), ret, navigation());
	}

	protected WebSocketResult createWebSocketResult(WebSocketNode node, String control, String action, String data, NavigateNode[] navi) {
		return createWebSocketResult(WebSocketResultType.Single, node, control, action, data, navi);
	}

	protected WebSocketResult createWebSocketResult(WebSocketResultType type, WebSocketNode node, String control, String action, String data, NavigateNode[] navi) {
		WebSocketResult ret = new WebSocketResult();
		ret.setType(type);
		ret.setControl(control);
		ret.setAction(action);
		ret.setData(data);
		ret.setNode(node);
		if (navi != null) {
			ret.addRangeNavigate(navi);
		}

		return ret;
	}

	protected String createNotification(NotificationType type, String msg) {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		if (type == NotificationType.Success) {
			obj.add("type", "success");
		} else if (type == NotificationType.Warning) {
			obj.add("type", "warning");
		} else {
			obj.add("type", "danger");
		}
		obj.add("msg", msg);
		return obj.build().toString();
	}
}
