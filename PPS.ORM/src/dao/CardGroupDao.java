package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.CardGroup;

public class CardGroupDao extends Dao<CardGroup> {

	private static List<CardGroup> singleton = null;

	protected CardGroupDao() {
		super(CardGroup.class);
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		if (singleton == null) {
			singleton = Manager.transaction(() -> {
				try {
					Query query = Manager.get().createNamedQuery("CardGroup.findAll", CardGroup.class);
					return (List<CardGroup>) query.getResultList();
				} catch (NoResultException e) {
					return null;
				}
			});
		}
	}

	public CardGroup getCardGroup(String group) {
		initialize();
		return singleton.stream().filter(x -> x.getCardGroup().equals(group)).findFirst().get();
	}

}