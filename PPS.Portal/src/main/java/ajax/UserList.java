package ajax;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import common.FactoryDao;
import common.IAjaxServlet;
import common.PermissionServlet;
import dao.UserDao;
import entity.bean.UserBean;
import model.User;
import reference.CardMaster;

@WebServlet("/userlist")
@PermissionServlet(CardMaster.USER_MANAGEMENT)
public class UserList extends IAjaxServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected String doAjax() {
		List<User> userlist = FactoryDao.getDao(UserDao.class).findAll();
		List<UserBean> data = new ArrayList<>();
		for (User user : userlist) {
			UserBean entity = new UserBean();
			entity.setId(user.getId());
			entity.setGiven_name(user.getGivenName());
			entity.setName(user.getName());
			entity.setNick_name(user.getNickName());
			entity.setCompany_name(user.getCompany().getName());
			entity.setGroup_name(user.getGroup().getName());
			entity.setType(user.getStateInfo().getState().getName());
			entity.setActive(user.getStateInfo().getIsDelete() ? "false" : "true");
			data.add(entity);
		}
		return getDataTableData(data);
	}

}
