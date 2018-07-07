package contoller;

import java.util.ArrayList;
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

@Workflow(name = "admin", cardrole = CardMaster.ADMIN)
public class AdminContoller extends IWorkflow {

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = super.getUserinfo(node.getSession()).getUser();
		List<TileBean> data = new ArrayList<>();
		for (model.Card card : CardRoleCache.getCardByUser(user)) {
			if (!Util.StringEquals(CardStepMaster.ADMIN, card.getCardStep().getStep())) {
				continue;
			}
			if (Util.StringEquals(CardTypeMaster.MOMAL_CARD, card.getCardType().getCardType())) {
				continue;
			}
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
