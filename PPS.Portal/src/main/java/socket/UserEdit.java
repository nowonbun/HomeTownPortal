package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "useredit")
public class UserEdit extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { 
			new NavigateNode("./#!/admin", "Admin"), 
			new NavigateNode("./#!/usermanagement", "UserManagement"),
			new NavigateNode("./#!/useredit", "Useredit") };

	@SuppressWarnings("unused")
	private class Node {

	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
