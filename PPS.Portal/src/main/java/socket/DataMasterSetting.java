package socket;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import common.IWorkflow;
import common.JsonConverter;
import common.Util;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.TileBean;
import model.User;
import reference.CardMaster;
import reference.CardRoleCache;
import reference.CardStepMaster;
import reference.CardTypeMaster;

@Workflow(name = "datamastersetting", cardrole = CardMaster.DATA_MASTER_SETTING)
public class DataMasterSetting extends IWorkflow {
	
	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = super.getUserinfo(node.getSession()).getUser();
		List<TileBean> data = new ArrayList<>();
		for (model.Card card : CardRoleCache.getCardByUser(user)) {
			if (!Util.StringEquals(CardStepMaster.MASTER, card.getCardStep().getStep())) {
				continue;
			}
			if (Util.StringEquals(CardTypeMaster.MOMAL_CARD, card.getCardType().getCardType())) {
				continue;
			}
			TileBean entity = new TileBean();
			if (Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.IMAGE_CARD) ||
				Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.IMAGE_MENU_CARD)) {
				entity.setTypeHeaderClass("card-image");
				if (card.getImg() != null) {
					entity.setBackground("url('data:image/jpg;base64,"
							+ Base64.getEncoder().encodeToString(card.getImg()) + "') center");
				} else {
					entity.setBackground("url('./contents/no_card.jpg') no-repeat center");
				}
				entity.setHeader("");
				entity.setBorder("");
				entity.setBody("<span class='card-image__body'>" + card.getName() + "</span>");
				entity.setMenu(Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.IMAGE_MENU_CARD));
				entity.setHref(card.getHref());
				entity.setControl(card.getControl());
				entity.setTemplate(card.getTemplate());
			} else if (Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.EVENT_CARD) ||
					   Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.EVENT_MENU_CARD)) {
				entity.setTypeHeaderClass("card-event");
				entity.setBackground(card.getColor());
				entity.setHeader(card.getTitle());
				entity.setBorder("mdl-card--border");
				entity.setBody(
						"<a class='mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect'>" + card.getName()
								+ "</a><div class='mdl-layout-spacer'></div><i class='" + card.getIcon() + "'></i>");
				entity.setMenu(Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.EVENT_MENU_CARD));
				entity.setHref(card.getHref());
				entity.setControl(card.getControl());
				entity.setTemplate(card.getTemplate());
			} else {
				continue;
			}
			data.add(entity);
		}
		return createWebSocketResult(JsonConverter.create(data), node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return null;
	}

}
