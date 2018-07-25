package contoller;

import common.Controller;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "main", cardrole = "")
public class MainContoller extends Controller {
	private static NavigateNode[] navi = new NavigateNode[] {};

	protected Class<?> setLogClass() {
		return MainContoller.class;
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult(node);
	}
}
