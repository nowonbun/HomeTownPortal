package ajax;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import common.IAjaxServlet;
import common.PermissionServlet;
import entity.bean.RoleBean;
import model.Role;
import reference.CardMaster;
import reference.RoleMaster;

@WebServlet("/actionrole")
@PermissionServlet(CardMaster.COMPANY_N_GROUP_SETTING)
public class ActionRole extends IAjaxServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected String doAjax() {
		List<Role> rolelist = RoleMaster.getDao().getRoleAll();
		List<RoleBean> data = new ArrayList<>();
		for (Role role : rolelist) {
			RoleBean entity = new RoleBean();
			entity.setCode(role.getRole());
			entity.setName(role.getName());
			data.add(entity);
		}
		return getDataTableData(data);
	}

}