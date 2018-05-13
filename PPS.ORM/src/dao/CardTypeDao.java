package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import common.MasterDao;
import model.CardStep;
import model.CardType;

public class CardTypeDao extends MasterDao<CardType> {

	protected CardTypeDao() {
		super(CardType.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<CardType> getDataList() {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get().createNamedQuery("CardType.findAll", CardType.class);
				return (List<CardType>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public CardType getCardType(String type) {
		return getData().stream().filter(x -> x.getCardType().equals(type)).findFirst().get();
	}

}