package socket;

import common.IWorkflow;
import common.Workflow;
import entity.WebSocketNode;

@Workflow(name = "main")
public class Main extends IWorkflow {

	@Override
	public String main(WebSocketNode node) {
		return "test";
	}

}
