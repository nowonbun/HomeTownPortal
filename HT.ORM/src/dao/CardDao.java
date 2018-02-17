package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.Card;

public class CardDao extends Dao<Card> {

	private static List<Card> singleton = null;

	protected CardDao() {
		super(Card.class);
	}

	@SuppressWarnings("unchecked")
	public Card getCard(String code) {
		if (singleton == null) {
			singleton = Manager.transaction(() -> {
				try {
					Query query = Manager.get().createNamedQuery("Card.findAll", Card.class);
					return (List<Card>) query.getResultList();
				} catch (NoResultException e) {
					return null;
				}
			});
		}
		return singleton.stream().filter(x -> x.getCode().equals(code)).findFirst().get();
	}

}
