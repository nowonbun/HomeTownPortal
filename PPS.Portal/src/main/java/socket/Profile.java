package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "profile")
public class Profile extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/profile", "Profile") };

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult("TEST", node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}