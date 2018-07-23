package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.MasterDao;
import common.Permission;
import model.Card;

//http://kwonnam.pe.kr/wiki/java/jpa/cache
public class CardDao extends MasterDao<Card> {

	protected CardDao() {
		super(Card.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Card> getDataList() {
		return transaction((em) -> {
			try {
				Query query = em.createNamedQuery("Card.findAll", Card.class);
				// SessionFactory sessionFactory = Manager.get().unwrap(SessionFactory.class);
				// org.hibernate.Cache cache = sessionFactory.getCache();
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

	public void deleteRole(String code) {
		transaction((em) -> {
			Query query = em.createNativeQuery(" DELETE FROM MAP_VIEW_ROLE_COMPANY WHERE CARD_CODE = ?");
			query.setParameter(1, code);
			query.executeUpdate();

			query = em.createNativeQuery(" DELETE FROM MAP_VIEW_ROLE_GROUP WHERE CARD_CODE = ?");
			query.setParameter(1, code);
			query.executeUpdate();

			query = em.createNativeQuery(" DELETE FROM MAP_VIEW_ROLE_USER WHERE CARD_CODE = ?");
			query.setParameter(1, code);
			query.executeUpdate();
		});
	}

	public void setRole(String code, List<Integer> coms, List<Integer> grps, List<String> usrs) {
		transaction((em) -> {
			Query query = null;
			for (Integer com : coms) {
				query = em.createNativeQuery(" INSERT INTO MAP_VIEW_ROLE_COMPANY (CARD_CODE, COMPANY_ID) VALUES(?, ?)");
				query.setParameter(1, code);
				query.setParameter(2, com);
				query.executeUpdate();
			}
			for (Integer grp : grps) {
				query = em.createNativeQuery(" INSERT INTO MAP_VIEW_ROLE_GROUP (CARD_CODE, GROUP_ID) VALUES(?, ?)");
				query.setParameter(1, code);
				query.setParameter(2, grp);
				query.executeUpdate();
			}
			for (String usr : usrs) {
				query = em.createNativeQuery(" INSERT INTO MAP_VIEW_ROLE_USER (CARD_CODE, USER_ID) VALUES(?, ?)");
				query.setParameter(1, code);
				query.setParameter(2, usr);
				query.executeUpdate();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermission() {
		return transaction((em) -> {
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
				Query query = em.createNativeQuery(sb.toString());
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
