package contoller;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import common.IWorkflow;
import common.JsonConverter;
import common.LoggerManager;
import common.Util;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.TileBean;
import model.User;
import reference.CardRoleCache;
import reference.CardStepMaster;
import reference.CardTypeMaster;

@Workflow(name = "card", cardrole = "")
public class CardContoller extends IWorkflow {

	Logger logger = LoggerManager.getLogger(CardContoller.class);

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = super.getUserinfo(node.getSession()).getUser();
		logger.debug("user - " + user.getGivenName());
		logger.debug(user.getGivenName());
		List<TileBean> data = new ArrayList<>();
		for (model.Card card : CardRoleCache.getCardByUser(user)) {
			if (!Util.StringEquals(CardStepMaster.HOME, card.getCardStep().getStep())) {
				continue;
			}
			if (Util.StringEquals(CardTypeMaster.MOMAL_CARD, card.getCardType().getCardType())) {
				continue;
			}
			logger.debug("card - " + card.getName());

			TileBean entity = createTile(card);
			if (entity != null) {
				data.add(entity);
			}
		}
		return createWebSocketResult(JsonConverter.create(data), node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return null;
	}

}
