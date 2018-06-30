package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import reference.CardMaster;

@Workflow(name = "actionrole", cardrole = CardMaster.ACTION_ROLE)
public class ActionRole extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { 
			new NavigateNode("./#!/admin", "Admin"), 
			new NavigateNode("./#!/actionrole", "actionrole")};

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
