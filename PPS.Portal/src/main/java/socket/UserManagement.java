package socket;

import java.util.ArrayList;
import java.util.List;
import common.FactoryDao;
import common.IWorkflow;
import common.JsonConverter;
import common.Workflow;
import dao.UserDao;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;
import model.State;
import model.User;
import reference.StateMaster;

@Workflow(name = "usermanagement")
public class UserManagement extends IWorkflow {

	private static NavigateNode[] navi = new NavigateNode[] { new NavigateNode("./#!/admin", "Admin"), new NavigateNode("./#!/usermanagement", "UserManagement") };

	@SuppressWarnings("unused")
	private class Node {
		String id;
		String given;
		String name;
		String nick;
		String company;
		String group;
		String type;
		String active;
	}

	@Override
	public WebSocketResult init(WebSocketNode node) {
		List<User> userlist = FactoryDao.getDao(UserDao.class).findAll();
		List<Node> data = new ArrayList<>();
		for (User user : userlist) {
			Node entity = new Node();
			entity.id = user.getId();
			entity.given = user.getGivenName();
			entity.name = user.getName();
			entity.nick = user.getNickName();
			entity.company = user.getCompany().getName();
			entity.group = user.getGroup().getName();
			entity.type = user.getStateInfo().getState().getName();
			entity.active = user.getStateInfo().getIsDelete() ? "false" : "true";
			data.add(entity);
		}
		return createWebSocketResult(JsonConverter.create(data), node);
	}

	@Override
	protected NavigateNode[] navigation() {
		return navi;
	}

}
