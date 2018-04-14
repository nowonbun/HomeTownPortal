package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "login")
public class Login extends IWorkflow {

	public static String OK = "OK";
	public static String NG = "NG";

	@Override
	public WebSocketResult init(WebSocketNode node) {
		if (!isAuth(node.getSession())) {
			return createWebSocketResult(Login.NG, node);
		}
		return createWebSocketResult(Login.OK, node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return null;
	}
}
