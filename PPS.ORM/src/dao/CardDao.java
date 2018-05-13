package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.Manager;
import common.MasterDao;
import model.Card;
import model.User;

public class CardDao extends MasterDao<Card> {

	protected CardDao() {
		super(Card.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Card> getDataList() {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get().createNamedQuery("Card.findAll", Card.class);
				return (List<Card>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public Card getCard(String code) {
		return getData().stream().filter(x -> x.getCode().equals(code)).findFirst().get();
	}

	public List<Card> getCardAll() {
		return getData();
	}

	@SuppressWarnings("unchecked")
	public List<Card> getCardbyUser(User user) {
		return Manager.transaction(() -> {
			try {
				/* 
				 * SELECT a.CODE, b.COMPANY_ID, c.GROUP_ID, d.USER_ID FROM MST_CARD a
				 * LEFT OUTER JOIN (SELECT x.* FROM MAP_VIEW_ROLE_COMPANY x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) b ON a.CODE = b.CARD_CODE
				 * LEFT OUTER JOIN (SELECT x.* FROM MAP_VIEW_ROLE_GROUP x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) c ON a.CODE = c.CARD_CODE
				 * LEFT OUTER JOIN (SELECT x.* FROM MAP_VIEW_ROLE_USER x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) d ON a.CODE = d.CARD_CODE
				 * ORDER BY a.ORDER_SEQ
				 */
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT a.CODE, b.COMPANY_ID, c.GROUP_ID, d.USER_ID FROM ");
				sb.append(" MST_CARD a ");
				sb.append(" LEFT OUTER JOIN (SELECT x.* FROM MAP_VIEW_ROLE_COMPANY x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) b ");
				sb.append(" ON a.CODE = b.CARD_CODE ");
				sb.append(" LEFT OUTER JOIN (SELECT x.* FROM MAP_VIEW_ROLE_GROUP x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) c ");
				sb.append(" ON a.CODE = c.CARD_CODE ");
				sb.append(" LEFT OUTER JOIN (SELECT x.* FROM MAP_VIEW_ROLE_USER x INNER JOIN TSN_STATE_INFO y ON x.STATE=y.IDX AND y.IS_DELETE = false ) d ");
				sb.append(" ON a.CODE = d.CARD_CODE ");
				sb.append(" ORDER BY a.ORDER_SEQ ");
				Query query = Manager.get().createNativeQuery(sb.toString());
				List<Object[]> roles = query.getResultList();
				List<Card> ret = new ArrayList<>();
				for (Object[] r : roles) {
					if (r[1] != null && (int) r[1] != user.getCompany().getId()) {
						continue;
					}
					if (r[2] != null && (int) r[2] != user.getGroup().getId()) {
						continue;
					}
					if (r[3] != null && user.getId().equals(r[3].toString())) {
						continue;
					}
					ret.add(getCard(r[0].toString()));
				}
				return ret;
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
