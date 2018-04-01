package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;

@Workflow(name = "profile")
public class Profile extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/profile", "Profile") };

	@Override
	public String main(WebSocketNode node) {
		if (!isAuth(node.getSession())) {
			return Login.NG;
		}
		return Login.OK;
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}