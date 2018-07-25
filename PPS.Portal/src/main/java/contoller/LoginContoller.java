package contoller;

import common.Controller;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "login", cardrole = "")
public class LoginContoller extends Controller {

	public static String OK = "OK";
	public static String NG = "NG";

	protected Class<?> setLogClass() {
		return LoginContoller.class;
	}

	@Override
	protected NavigateNode[] navigation() {
		return null;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		if (!isAuth(node.getSession())) {
			return createWebSocketResult(NG, node);
		}
		return createWebSocketResult(OK, node);
	}
}
