package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.Manager;
import common.MasterDao;
import common.Permission;
import model.Card;

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
		try {
			return getData().stream().filter(x -> x.getCode().equals(code)).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public List<Card> getCardAll() {
		return getData();
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermission() {
		return Manager.transaction(() -> {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT a.CODE, b.COMPANY_ID, c.GROUP_ID, d.USER_ID FROM ");
				sb.append(" MST_CARD a ");
				sb.append(" LEFT OUTER JOIN MAP_VIEW_ROLE_COMPANY b ");
				sb.append(" ON a.CODE = b.CARD_CODE ");
				sb.append(" LEFT OUTER JOIN MAP_VIEW_ROLE_GROUP c ");
				sb.append(" ON a.CODE = c.CARD_CODE ");
				sb.append(" LEFT OUTER JOIN MAP_VIEW_ROLE_USER d ");
				sb.append(" ON a.CODE = d.CARD_CODE ");
				sb.append(" ORDER BY a.ORDER_SEQ ");
				Query query = Manager.get().createNativeQuery(sb.toString());
				List<Permission> ret = new ArrayList<>();
				List<Object[]> roles = query.getResultList();
				for (Object[] node : roles) {
					ret.add(new Permission(node[0], node[1], node[2], node[3]));
				}
				return ret;
			} catch (NoResultException e) {
				return null;
			}
		});
	}
}
