package contoller;

import java.util.ArrayList;
import java.util.List;
import common.Controller;
import common.JsonConverter;
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
public class CardContoller extends Controller {

	protected Class<?> setLogClass() {
		return CardContoller.class;
	}

	@Override
	protected NavigateNode[] navigation() {
		return null;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = super.getUserinfo(node.getSession()).getUser();
		getLogger().debug("user - " + user.getGivenName());
		getLogger().debug(user.getGivenName());
		List<TileBean> data = new ArrayList<>();
		for (model.Card card : CardRoleCache.getCardByUser(user)) {
			if (!Util.StringEquals(CardStepMaster.HOME, card.getCardStep().getStep())) {
				continue;
			}
			if (Util.StringEquals(CardTypeMaster.MOMAL_CARD, card.getCardType().getCardType())) {
				continue;
			}
			getLogger().debug("card - " + card.getName());

			TileBean entity = createTile(card);
			if (entity != null) {
				data.add(entity);
			}
		}
		return createWebSocketResult(JsonConverter.create(data), node);
	}
}
