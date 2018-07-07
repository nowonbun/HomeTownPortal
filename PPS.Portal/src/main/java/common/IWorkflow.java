package common;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import entity.bean.TileBean;
import reference.CardTypeMaster;

public abstract class IWorkflow extends ICommon {
	public static String Login = "login";
	public static String Init = "init";
	public static String Permission = "permission";

	public abstract WebSocketResult init(WebSocketNode node);

	protected abstract NavigateNode[] navigation();

	protected WebSocketResult createWebSocketError(WebSocketNode node) {
		return createWebSocketResult(WebSocketResultType.Single, node, node.getControl(), node.getAction(), createNotification(NotificationType.Danger, "System error"), navigation());
	}

	protected WebSocketResult createWebSocketResult(WebSocketNode node) {
		return createWebSocketResult(WebSocketResultType.Single, node, node.getControl(), node.getAction(), "", navigation());
	}

	protected WebSocketResult createWebSocketResult(String ret, WebSocketNode node) {
		return createWebSocketResult(WebSocketResultType.Single, node, node.getControl(), node.getAction(), ret, navigation());
	}

	protected WebSocketResult createWebSocketResult(WebSocketResultType type, String ret, WebSocketNode node) {
		return createWebSocketResult(type, node, node.getControl(), node.getAction(), ret, navigation());
	}

	protected WebSocketResult createWebSocketResult(WebSocketNode node, String control, String action, String data, NavigateNode[] navi) {
		return createWebSocketResult(WebSocketResultType.Single, node, control, action, data, navi);
	}

	protected WebSocketResult createWebSocketResult(WebSocketResultType type, WebSocketNode node, String control, String action, String data, NavigateNode[] navi) {
		WebSocketResult ret = new WebSocketResult();
		ret.setType(type);
		ret.setControl(control);
		ret.setAction(action);
		ret.setData(data);
		ret.setNode(node);
		if (navi != null) {
			ret.addRangeNavigate(navi);
		}

		return ret;
	}

	protected WebSocketResult createNotificationResult(NotificationType type, String msg, WebSocketNode node) {
		return createWebSocketResult(createNotification(type, msg), node);
	}

	protected String createNotification(NotificationType type, String msg) {
		JsonObjectBuilder obj = Json.createObjectBuilder();
		if (type == NotificationType.Success) {
			obj.add("type", "success");
		} else if (type == NotificationType.Warning) {
			obj.add("type", "warning");
		} else {
			obj.add("type", "danger");
		}
		obj.add("msg", msg);
		return obj.build().toString();
	}

	protected TileBean createTile(model.Card card) {

		if (Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.IMAGE_CARD) || Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.IMAGE_MENU_CARD)) {
			TileBean entity = new TileBean();
			entity.setTypeHeaderClass("card-image");
			if (card.getImg() != null) {
				entity.setBackground("url('" + new String(card.getImg()) + "') no-repeat center");
			} else {
				entity.setBackground("url('./contents/no_card.jpg') no-repeat center");
			}
			entity.setBorder("");
			entity.setHeader("");
			entity.setBody("<span class='card-caption-body'>" + card.getTitle() + "</span>");
			entity.setMenu(Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.IMAGE_MENU_CARD));
			entity.setHref(card.getHref());
			entity.setControl(card.getControl());
			entity.setTemplate(card.getTemplate());
			return entity;
		} else if (Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.EVENT_CARD) || Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.EVENT_MENU_CARD)) {
			TileBean entity = new TileBean();
			entity.setTypeHeaderClass("card-event");
			entity.setBackground("");
			entity.setBorder("mdl-card--border");
			entity.setHeader("<div class='card-icon'><i class='" + card.getIcon() + "' style='color:" + card.getColor() + ";'></i></div>");
			entity.setBody("<span class='card-caption-body'>" + card.getTitle() + "</span><div class='mdl-layout-spacer'></div>");
			entity.setMenu(Util.StringEquals(card.getCardType().getCardType(), CardTypeMaster.EVENT_MENU_CARD));
			entity.setHref(card.getHref());
			entity.setControl(card.getControl());
			entity.setTemplate(card.getTemplate());
			return entity;
		}
		return null;
	}
}
