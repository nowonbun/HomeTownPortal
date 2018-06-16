package ajax;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import common.FactoryDao;
import common.IAjaxServlet;
import dao.UserDao;
import model.User;

@WebServlet("/userlist")
public class UserList extends IAjaxServlet {

	private static final long serialVersionUID = 1L;

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
	protected String doAjax() {
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
		return getDataTableData(data);
	}

}
