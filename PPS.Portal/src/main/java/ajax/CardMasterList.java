package ajax;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import common.IAjaxServlet;
import entity.bean.CardBean;
import model.Card;
import reference.CardMaster;

@WebServlet("/cardmasterlist")
public class CardMasterList extends IAjaxServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected String doAjax() {
		if (!isRoleCheck(getUserinfo().getUser(), CardMaster.CARD_MASTER_SETTING)) {
			setStatus(401);
			return "";
		}
		List<Card> cardlist = CardMaster.getDao().getCardAll();
		List<CardBean> data = new ArrayList<>();
		for (Card card : cardlist) {
			CardBean entity = new CardBean();
			entity.setCode(card.getCode());
			entity.setName(card.getName());
			entity.setTitle(card.getTitle());
			entity.setDescription(card.getDescription());
			entity.setHasImg(card.getImg() != null);
			entity.setIcon(card.getIcon());
			entity.setColor(card.getColor());
			entity.setType(card.getCardType().getName());
			entity.setSeq(card.getOrderSeq());
			entity.setStep(card.getCardStep().getName());
			data.add(entity);
		}
		return getDataTableData(data);
	}

}