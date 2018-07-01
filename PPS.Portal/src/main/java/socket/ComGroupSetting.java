package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import reference.CardMaster;

@Workflow(name = "comgroupsetting", cardrole = CardMaster.COMPANY_N_GROUP_SETTING)
public class ComGroupSetting extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { 
			new NavigateNode("./#!/admin", "Admin"), 
			new NavigateNode("./#!/comgroupsetting", "Company & Group Setting")};

	@Override
	public WebSocketResult init(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
