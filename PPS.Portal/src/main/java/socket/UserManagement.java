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

	public WebSocketResult initAdd(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	public WebSocketResult initEdit(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	public WebSocketResult initDelete(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

}
