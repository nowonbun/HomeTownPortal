package common;

import entity.WebSocketNode;
import entity.WebSocketResult;

public abstract class IWorkflow extends ICommon {
	public WebSocketResult run(WebSocketNode node) {
		return createWebSocketResult(node, getKey(), main(node));
	}

	protected abstract String main(WebSocketNode node);

	protected WebSocketResult createWebSocketResult(WebSocketNode node, String key, String data) {
		return createWebSocketResult(WebSocketResultType.Single, node, key, data);
	}

	protected WebSocketResult createWebSocketResult(WebSocketResultType type, WebSocketNode node, String key,
			String data) {
		WebSocketResult ret = new WebSocketResult();
		ret.setType(type);
		ret.setKey(key);
		ret.setData(data);
		ret.setNode(node);
		return ret;
	}

	protected String getKey() {
		return this.getClass().getAnnotation(Workflow.class).name();
	}
}
