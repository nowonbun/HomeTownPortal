package contoller;

import common.IWorkflow;
import common.JsonConverter;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.ObjectBean;
import reference.CardMaster;

@Workflow(name = "actionrole", cardrole = CardMaster.ACTION_ROLE)
public class ActionRoleController extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { 
			new NavigateNode(CardMaster.getAdminCard()), 
			new NavigateNode(CardMaster.getActionRoleCard())};

	@Override
	public WebSocketResult init(WebSocketNode node) {
		ObjectBean data = new ObjectBean();
		data.setData("actionrole");
		return createWebSocketResult(JsonConverter.create(data), node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
