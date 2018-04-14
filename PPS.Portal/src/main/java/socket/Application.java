package socket;

import common.IWorkflow;
import common.JsonConverter;
import common.Workflow;
import dao.ApplicationDao;
import dao.FactoryDao;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import model.User;

@Workflow(name = "application")
public class Application extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/Application", "Application") };

	@SuppressWarnings("unused")
	private class Node {
		String given_name;
		String name;
		String nick_name;
		boolean is_img_blob;
		String img_url;
		byte[] img_blob;
		String comment;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		User user = getUserinfo(node.getSession()).getUser();
		Node data = new Node();
		data.given_name = user.getGivenName();
		data.name = user.getName();
		data.nick_name = user.getNickName();
		if (user.getImgBlob() == null) {
			data.is_img_blob = false;
			data.img_url = user.getImgUrl();
		} else {
			data.is_img_blob = true;
			data.img_blob = user.getImgBlob();
		}
		model.Application app = FactoryDao.getDao(ApplicationDao.class).getEntiryApplyingRecently(user.getId());
		if (app != null) {
			data.comment = app.getComment().getComment();
		}

		return createWebSocketResult(JsonConverter.create(data), node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
