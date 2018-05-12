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
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import model.CardStep;
import model.CardType;
import model.Group;

@Workflow(name = "admin")
public class Admin extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/admin", "Admin") };

	@SuppressWarnings("unused")
	private class Node {
		String typeHeaderClass;
		String background;
		String header;
		String border;
		String body;
		String href;
		String group;
	}

	private boolean containGroup(model.Card card, String code) {
		/*for (Group g : card.getGroups()) {
			if (Util.StringEquals(g.getCode(), code)) {
				return true;
			}
		}*/
		return false;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		List<model.Card> cards = MasterDao.getDao(CardDao.class).getCardAll();
		List<Node> data = new ArrayList<>();
		/*
		Group group = super.getUserinfo(node.getSession()).getUser().getGroup();
		for (model.Card card : cards) {
			if (!containGroup(card, group.getCode())) {
				continue;
			}
			if (!Util.StringEquals(CardStep.ADMIN, card.getCardStep().getStep())) {
				continue;
			}
			Node entity = new Node();
			if (Util.StringEquals(card.getCardType().getCardType(), CardType.IMAGE)) {
				entity.typeHeaderClass = "card-image";
				if (card.getImg() != null) {
					entity.background = "url('data:image/jpg;base64,"
							+ Base64.getEncoder().encodeToString(card.getImg()) + "') center / cover";
				} else {
					entity.background = "url('./contents/no_card.jpg') center / cover";
				}
				entity.header = "";
				entity.border = "";
				entity.body = "<span class='card-image__body'>" + card.getDescription() + "</span>";
				entity.href = card.getHref();
				entity.group = card.getCardGroup().getName();
			} else if (Util.StringEquals(card.getCardType().getCardType(), CardType.EVENT)) {
				entity.typeHeaderClass = "card-event";
				entity.background = card.getColor();
				entity.header = card.getTitle();
				entity.border = "mdl-card--border";
				entity.body = "<a class='mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect'>"
						+ card.getDescription() + "</a><div class='mdl-layout-spacer'></div><i class='" + card.getIcon()
						+ "'></i>";
				entity.href = card.getHref();
				entity.group = card.getCardGroup().getName();
			} else {
				continue;
			}
			data.add(entity);
		}*/
		return createWebSocketResult(JsonConverter.create(data), node);

	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
