package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;

@Workflow(name = "application")
public class Application extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/Application", "Application") };

	@Override
	protected String main(WebSocketNode node) {
		return "Hello world";
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
