package ajax;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import common.IAjaxServlet;
import common.PermissionServlet;
import entity.bean.RoleBean;
import model.Card;
import reference.CardMaster;

@WebServlet("/cardviewrole")
@PermissionServlet(CardMaster.VIEW_ROLE)
public class CardViewRole extends IAjaxServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected String doAjax() {
		List<Card> cardlist = CardMaster.getDao().getCardAll();
		List<RoleBean> data = new ArrayList<>();
		for (Card card : cardlist) {
			RoleBean entity = new RoleBean();
			entity.setCode(card.getCode());
			entity.setName(card.getName());
			data.add(entity);
		}
		return getDataTableData(data);
	}

}