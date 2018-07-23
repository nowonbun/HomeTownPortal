package dao;

import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.MasterDao;
import model.CardType;

public class CardTypeDao extends MasterDao<CardType> {

	protected CardTypeDao() {
		super(CardType.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<CardType> getDataList() {
		return transaction((em) -> {
			try {
				Query query = em.createNamedQuery("CardType.findAll", CardType.class);
				return (List<CardType>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public CardType getCardType(String type) {
		try {
			return getData().stream().filter(x -> x.getCardType().equals(type)).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}