package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import reference.CardMaster;

@Workflow(name = "cardviewrole", cardrole = CardMaster.VIEW_ROLE)
public class CardViewRole extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { 
			new NavigateNode("./#!/admin", "Admin"), 
			new NavigateNode("./#!/cardviewrole", "CardViewRole")};

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
