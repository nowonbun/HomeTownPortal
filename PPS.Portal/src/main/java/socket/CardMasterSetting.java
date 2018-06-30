package socket;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.ObjectBean;
import reference.CardMaster;

@Workflow(name = "cardmastersetting", cardrole = CardMaster.CARD_MASTER_SETTING)
public class CardMasterSetting extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { 
			new NavigateNode("./#!/admin", "Admin"),
			new NavigateNode("./#!/datamastersetting", "Master setting"),
			new NavigateNode("./#!/cardmastersetting", "Card Master setting") };

	@Override
	public WebSocketResult init(WebSocketNode node) {
		ObjectBean data = new ObjectBean();
		data.setData("cardmasterlist");
		return createWebSocketResult(data.toJson(), node);
	}

	public WebSocketResult initEdit(WebSocketNode node) {
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

}
