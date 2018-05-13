package socket;

import common.IWorkflow;
import common.JsonConverter;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import model.User;

@Workflow(name = "profile")
public class Profile extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/profile", "Profile") };

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
		/*model.Application app = FactoryDao.getDao(ApplicationDao.class).getEntiryApplyingRecently(user.getId());
		if (app != null) {
			data.comment = app.getComment().getComment();
		}*/

		return createWebSocketResult(JsonConverter.create(data), node);
	}

	public WebSocketResult apply(WebSocketNode node) {
		JsonConverter.parse(node.getData(), (data) -> {
			/*
			User user = getUserinfo(node.getSession()).getUser();
			model.Application app = FactoryDao.getDao(ApplicationDao.class).getEntiryApplyingRecently(user.getId());
			user.setGivenName(data.getString("given_name"));
			user.setName(data.getString("name"));
			user.setNickName(data.getString("nick_name"));
			if (data.getBoolean("is_img_blob")) {
				byte[] blob = data.getString("img_url").getBytes();
				user.setImgBlob(blob);
			} else {
				user.setImgUrl(data.getString("img_url"));
			}
			if (app == null) {
				app = new model.Application(user, user.getId());
				app.setComment(new Comment(user.getId()));
				app.getComment().setComment(data.getString("comment"));
				FactoryDao.getDao(ApplicationDao.class).create(app);
			} else {
				app.getComment().setComment(data.getString("comment"));
				app.updateTransation(user.getId());
				app.getComment().updateTransation(user.getId());
				FactoryDao.getDao(ApplicationDao.class).update(app);
			}
			FactoryDao.getDao(UserDao.class).update(user);*/
		});
		return createWebSocketResult(node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}
}
