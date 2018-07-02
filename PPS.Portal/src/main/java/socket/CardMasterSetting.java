package socket;

import java.util.ArrayList;

import common.IWorkflow;
import common.Workflow;
import entity.NavigateNode;
import entity.SelectNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.CardBean;
import entity.bean.ObjectBean;
import reference.CardMaster;
import reference.CardTypeMaster;

@Workflow(name = "cardmastersetting", cardrole = CardMaster.CARD_MASTER_SETTING)
public class CardMasterSetting extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { 
			new NavigateNode(CardMaster.getAdminCard()),
			new NavigateNode(CardMaster.getDataMasterSettingCard()),
			new NavigateNode(CardMaster.getCardMasterSettingCard()) };

	@Override
	public WebSocketResult init(WebSocketNode node) {
		ObjectBean data = new ObjectBean();
		data.setData("cardmasterlist");
		return createWebSocketResult(data.toJson(), node);
	}

	public WebSocketResult initEdit(WebSocketNode node) {
		String code = node.getData();
		model.Card card = CardMaster.getDao().getCard(code);
		CardBean entity = new CardBean();
		entity.setCode(card.getCode());
		entity.setStep(card.getCardStep().getName());
		entity.setController(card.getControl());
		entity.setTemplate(card.getTemplate());
		entity.setName(card.getName());
		entity.setSeq(card.getOrderSeq());
		entity.setType(card.getCardType().getCardType());
		entity.setImg(card.getImg());
		entity.setTitle(card.getTitle());
		entity.setIcon(card.getIcon());
		entity.setColor(card.getColor());
		entity.setCardtypelist(new ArrayList<>());
		CardTypeMaster.getDao().getData().forEach(x -> {
			SelectNode select = new SelectNode();
			entity.getCardtypelist().add(select);
			select.setName(x.getName());
			select.setValue(x.getCardType());
		});
		return createWebSocketResult(entity.toJson(), node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

}
