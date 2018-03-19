package common;

import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

public abstract class IWorkflow extends ICommon {
	public WebSocketResult run(WebSocketNode node) {

		return createWebSocketResult(node, getKey(), main(node), navigation());
	}

	protected abstract String main(WebSocketNode node);

	protected abstract NavigateNode[] navigation();

	protected WebSocketResult createWebSocketResult(WebSocketNode node, String key, String data, NavigateNode[] navi) {
		return createWebSocketResult(WebSocketResultType.Single, node, key, data, navi);
	}

	protected WebSocketResult createWebSocketResult(WebSocketResultType type, WebSocketNode node, String key,
			String data, NavigateNode[] navi) {
		WebSocketResult ret = new WebSocketResult();
		ret.setType(type);
		ret.setKey(key);
		ret.setData(data);
		ret.setNode(node);
		if (navi != null) {
			ret.addRangeNavigate(navi);
		}

		return ret;
	}

	protected String getKey() {
		return this.getClass().getAnnotation(Workflow.class).name();
	}
}
