package ajax;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import common.FactoryDao;
import common.IAjaxServlet;
import common.PermissionServlet;
import dao.CompanyDao;
import entity.bean.CompanyBean;
import model.Company;
import model.Group;
import reference.CardMaster;

@WebServlet("/comgroupsetting")
@PermissionServlet(CardMaster.COMPANY_N_GROUP_SETTING)
public class ComNGroupSetting extends IAjaxServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected String doAjax() {
		// TODO: When this company is deleted, the user can run login.
		List<Company> comlist = FactoryDao.getDao(CompanyDao.class).getCompanyAllIncludeDelete();
		List<CompanyBean> data = new ArrayList<>();
		for (Company com : comlist) {
			for (Group grp : com.getGroups()) {
				CompanyBean entity = new CompanyBean();
				entity.setId(String.format("%d-%d", com.getId(), grp.getId()));
				entity.setName(com.getName());
				entity.setGroupname(grp.getName());
				entity.setActive(!grp.getStateInfo().getIsDelete());
				data.add(entity);
			}
		}
		return getDataTableData(data);
	}

}