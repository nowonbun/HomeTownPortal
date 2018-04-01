package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.CardType;

public class CardTypeDao extends Dao<CardType> {

	private static List<CardType> singleton = null;

	protected CardTypeDao() {
		super(CardType.class);
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		if (singleton == null) {
			singleton = Manager.transaction(() -> {
				try {
					Query query = Manager.get().createNamedQuery("CardType.findAll", CardType.class);
					return (List<CardType>) query.getResultList();
				} catch (NoResultException e) {
					return null;
				}
			});
		}
	}

	public CardType getCardType(String type) {
		initialize();
		return singleton.stream().filter(x -> x.getCardType().equals(type)).findFirst().get();
	}

}