package socket;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import common.IWorkflow;
import common.JsonConverter;
import common.Util;
import common.Workflow;
import dao.CardDao;
import dao.MasterDao;
import entity.WebSocketNode;
import model.CardType;

@Workflow(name = "card")
public class Card extends IWorkflow {
	@SuppressWarnings("unused")
	private class Node {
		String typeHeaderClass;
		String background;
		String header;
		String border;
		String body;
		String href;
	}

	@Override
	public String main(WebSocketNode node) {
		List<model.Card> cards = MasterDao.getDao(CardDao.class).getCardAll();
		List<Node> data = new ArrayList<>();
		for (model.Card card : cards) {
			Node entity = new Node();
			if (Util.StringEquals(card.getCardType().getCardType(), CardType.IMG)) {
				entity.typeHeaderClass = "card-image";
				entity.background = "url('data:image/jpg;base64," + Base64.getEncoder().encodeToString(card.getImg())
						+ "') center / cover";
				entity.header = "";
				entity.border = "";
				entity.body = "<span class='card-image__body'>" + card.getDescription() + "</span>";
				entity.href = card.getHref();
			} else if (Util.StringEquals(card.getCardType().getCardType(), CardType.IMG)) {
				entity.typeHeaderClass = "card-event";
				entity.background = card.getColor();
				entity.header = card.getTitle();
				entity.border = "mdl-card--border";
				entity.body = "<a class='mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect'>"
						+ card.getDescription() + "</a><div class='mdl-layout-spacer'></div><i class='" + card.getIcon()
						+ "'></i>";
				entity.href = card.getHref();
			} else {
				continue;
			}
			data.add(entity);
		}
		return JsonConverter.create(data);
	}

}
