package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "main")
public class Main extends IWorkflow {

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult("TEST", node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return null;
	}

}
