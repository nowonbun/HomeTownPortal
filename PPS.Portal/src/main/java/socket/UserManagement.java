package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "usermanagement")
public class UserManagement extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/admin", "Admin"), new NavigateNode("./#!/usermanagement", "UserManagement") };

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult("userlist", node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

}
