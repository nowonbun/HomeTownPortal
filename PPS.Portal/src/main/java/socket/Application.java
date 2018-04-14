package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "application")
public class Application extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/Application", "Application") };

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult("TEST", node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
