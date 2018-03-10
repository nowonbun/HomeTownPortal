package socket;

import common.IWorkflow;
import common.Workflow;
import entity.WebSocketNode;

@Workflow(name = "login")
public class Login extends IWorkflow {

	public static String OK = "OK";
	public static String NG = "NG";

	@Override
	public String main(WebSocketNode node) {
		if (!isAuth(node.getSession())) {
			return Login.NG;
		}
		return Login.OK;
	}
}
