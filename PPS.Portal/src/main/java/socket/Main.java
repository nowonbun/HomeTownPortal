package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@Workflow(name = "main", cardrole = "")
public class Main extends IWorkflow {

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return null;
	}
}
