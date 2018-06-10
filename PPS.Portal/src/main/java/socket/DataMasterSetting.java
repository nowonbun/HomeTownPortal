package socket;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import common.FactoryDao;
import common.IWorkflow;
import common.JsonConverter;
import common.Util;
import common.Workflow;
import dao.CardDao;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import model.CardStep;
import model.CardType;
import model.User;

@Workflow(name = "datamastersetting")
public class DataMasterSetting extends IWorkflow {

	@SuppressWarnings("unused")
	private class Node {
		String typeHeaderClass;
		String background;
		String header;
		String border;
		String body;
		String href;
	}

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/admin", "Admin"), new NavigateNode("./#!/datamastersetting", "Master setting") };

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = super.getUserinfo(node.getSession()).getUser();
		List<model.Card> cards = FactoryDao.getDao(CardDao.class).getCardbyUser(user);
		List<Node> data = new ArrayList<>();
		for (model.Card card : cards) {
			if (!Util.StringEquals(CardStep.MAST, card.getCardStep().getStep())) {
				continue;
			}
			Node entity = new Node();
			if (Util.StringEquals(card.getCardType().getCardType(), CardType.IMAGE)) {
				entity.typeHeaderClass = "card-image";
				if (card.getImg() != null) {
					entity.background = "url('data:image/jpg;base64," + Base64.getEncoder().encodeToString(card.getImg()) + "') center";
				} else {
					entity.background = "url('./contents/no_card.jpg') no-repeat center";
				}
				entity.header = "";
				entity.border = "";
				entity.body = "<span class='card-image__body'>" + card.getName() + "</span>";
				entity.href = card.getHref();
			} else if (Util.StringEquals(card.getCardType().getCardType(), CardType.EVENT)) {
				entity.typeHeaderClass = "card-event";
				entity.background = card.getColor();
				entity.header = card.getTitle();
				entity.border = "mdl-card--border";
				entity.body = "<a class='mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect'>" + card.getName() + "</a><div class='mdl-layout-spacer'></div><i class='" + card.getIcon()
						+ "'></i>";
				entity.href = card.getHref();
			} else {
				continue;
			}
			data.add(entity);
		}
		return createWebSocketResult(JsonConverter.create(data), node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

}
