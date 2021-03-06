package contoller;

import java.util.ArrayList;
import common.Controller;
import common.FactoryDao;
import common.JsonConverter;
import common.NotificationType;
import common.Util;
import common.Workflow;
import dao.CardDao;
import entity.NavigateNode;
import entity.SelectNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.CardBean;
import entity.bean.ObjectBean;
import reference.CardMaster;
import reference.CardTypeMaster;

@Workflow(name = "cardmastersetting", cardrole = CardMaster.CARD_MASTER_SETTING)
public class CardMasterSettingController extends Controller {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode(CardMaster.getAdminCard()), new NavigateNode(CardMaster.getDataMasterSettingCard()), new NavigateNode(CardMaster.getCardMasterSettingCard()) };

	protected Class<?> setLogClass() {
		return CardMasterSettingController.class;
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

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
		if (card.getImg() != null && (CardTypeMaster.equals(card.getCardType(), CardTypeMaster.getImageCardType()) || CardTypeMaster.equals(card.getCardType(), CardTypeMaster.getImageMenuCardType()))) {
			entity.setImg(new String(card.getImg()));
		} else {
			entity.setImg(null);
		}
		entity.setTitle(card.getTitle());
		entity.setIcon(card.getIcon());
		entity.setColor(card.getColor());
		entity.setCardtypelist(new ArrayList<>());
		if (CardTypeMaster.equals(card.getCardType(), CardTypeMaster.getEventCardType()) || CardTypeMaster.equals(card.getCardType(), CardTypeMaster.getImageCardType())) {
			SelectNode select = new SelectNode();
			entity.getCardtypelist().add(select);
			select.setName(CardTypeMaster.getEventCardType().getName());
			select.setValue(CardTypeMaster.getEventCardType().getCardType());
			select = new SelectNode();
			entity.getCardtypelist().add(select);
			select.setName(CardTypeMaster.getImageCardType().getName());
			select.setValue(CardTypeMaster.getImageCardType().getCardType());
		}
		if (CardTypeMaster.equals(card.getCardType(), CardTypeMaster.getEventMenuCardType()) || CardTypeMaster.equals(card.getCardType(), CardTypeMaster.getImageMenuCardType())) {
			SelectNode select = new SelectNode();
			entity.getCardtypelist().add(select);
			select.setName(CardTypeMaster.getEventMenuCardType().getName());
			select.setValue(CardTypeMaster.getEventMenuCardType().getCardType());
			select = new SelectNode();
			entity.getCardtypelist().add(select);
			select.setName(CardTypeMaster.getImageMenuCardType().getName());
			select.setValue(CardTypeMaster.getImageMenuCardType().getCardType());
		}
		if (CardTypeMaster.equals(card.getCardType(), CardTypeMaster.getModalCardType())) {
			SelectNode select = new SelectNode();
			entity.getCardtypelist().add(select);
			select.setName(CardTypeMaster.getModalCardType().getName());
			select.setValue(CardTypeMaster.getModalCardType().getCardType());
		}
		return createWebSocketResult(entity.toJson(), node);
	}

	public WebSocketResult applyEdit(WebSocketNode node) {
		try {
			CardBean bean = new CardBean();
			JsonConverter.parseObject(node.getData(), (data) -> {
				bean.setCode(Util.JsonString(data, "code"));
				bean.setName(Util.JsonString(data, "name"));
				bean.setSeq(Util.JsonInteger(data, "seq"));
				bean.setType(Util.JsonString(data, "type"));
				bean.setTitle(Util.JsonString(data, "title"));
				bean.setIcon(Util.JsonString(data, "icon"));
				bean.setColor(Util.JsonString(data, "color"));
				bean.setImg(Util.JsonString(data, "img"));
			});
			model.Card entity = FactoryDao.getDao(CardDao.class).getCard(bean.getCode());
			entity.setName(bean.getName());
			entity.setOrderSeq(bean.getSeq());
			entity.setCardType(CardTypeMaster.getDao().getCardType(bean.getType()));
			entity.setTitle(bean.getTitle());
			entity.setIcon(bean.getIcon());
			entity.setColor(bean.getColor());
			if (bean.getImg() != null) {
				entity.setImg(bean.getImg().getBytes());
			}
			CardMaster.getDao().update(entity);
			CardMaster.getDao().reset();
			return createWebSocketResult(createNotification(NotificationType.Success, "The card-master is updated"), node);
		} catch (Throwable e) {
			getLogger().error(e);
			return createWebSocketError(node);
		}
	}

}
