package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;

@Workflow(name = "admin")
public class Admin extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/admin", "Admin") };

	@Override
	protected String main(WebSocketNode node) {

		return "OK";
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
