package socket;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import common.FactoryDao;
import common.IWorkflow;
import common.JsonConverter;
import common.LoggerManager;
import common.Util;
import common.Workflow;
import dao.CardDao;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.TileBean;
import model.CardStep;
import model.CardType;
import model.User;

@Workflow(name = "card")
public class Card extends IWorkflow {

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = super.getUserinfo(node.getSession()).getUser();
		LoggerManager.getLogger(Card.class).debug("user - " + user.getGivenName());
		List<model.Card> cards = FactoryDao.getDao(CardDao.class).getCardbyUser(user);
		LoggerManager.getLogger(Card.class).debug("card count - " + cards.size());
		LoggerManager.getLogger(Card.class).debug(user.getGivenName());
		List<TileBean> data = new ArrayList<>();
		for (model.Card card : cards) {
			if (!Util.StringEquals(CardStep.HOME, card.getCardStep().getStep())) {
				continue;
			}
			LoggerManager.getLogger(Card.class).debug("card - " + card.getName());
			TileBean entity = new TileBean();
			if (Util.StringEquals(card.getCardType().getCardType(), CardType.IMAGE)) {
				entity.setTypeHeaderClass("card-image");
				if (card.getImg() != null) {
					entity.setBackground("url('data:image/jpg;base64,"
							+ Base64.getEncoder().encodeToString(card.getImg()) + "') no-repeat center");
				} else {
					entity.setBackground("url('./contents/no_card.jpg') no-repeat center");
				}
				entity.setHeader("");
				entity.setBorder("");
				entity.setBody("<span class='card-image__body'>" + card.getName() + "</span>");
				entity.setHref(card.getHref());
			} else if (Util.StringEquals(card.getCardType().getCardType(), CardType.EVENT)) {
				entity.setTypeHeaderClass("card-event");
				entity.setBackground(card.getColor());
				entity.setHeader(card.getTitle());
				entity.setBorder("mdl-card--border");
				entity.setBody(
						"<a class='mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect'>" + card.getName()
								+ "</a><div class='mdl-layout-spacer'></div><i class='" + card.getIcon() + "'></i>");
				entity.setHref(card.getHref());
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
