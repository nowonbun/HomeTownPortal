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
import reference.CardMaster;
import reference.CardRoleCache;
import reference.CardStepMaster;
import reference.CardTypeMaster;

@Workflow(name = "datamastersetting", cardrole = CardMaster.DATA_MASTER_SETTING)
public class DataMasterSettingContoller extends Controller {

	protected Class<?> setLogClass() {
		return DataMasterSettingContoller.class;
	}

	@Override
	protected NavigateNode[] navigation() {
		return null;
	}

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
			TileBean entity = createTile(card);
			if (entity != null) {
				data.add(entity);
			}
		}
		return createWebSocketResult(JsonConverter.create(data), node);
	}
}
