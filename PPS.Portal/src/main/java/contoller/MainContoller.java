package contoller;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "main", cardrole = "")
public class MainContoller extends IWorkflow {
	private static NavigateNode[] navi = new NavigateNode[] { };
	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
