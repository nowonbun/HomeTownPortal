package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.CardStep;

public class CardStepDao extends Dao<CardStep> {

	private static List<CardStep> singleton = null;

	protected CardStepDao() {
		super(CardStep.class);
		initialize();
	}
	
	@SuppressWarnings("unchecked")
	private void initialize() {
		if (singleton == null) {
			singleton = Manager.transaction(() -> {
				try {
					Query query = Manager.get().createNamedQuery("CardStep.findAll", CardStep.class);
					return (List<CardStep>) query.getResultList();
				} catch (NoResultException e) {
					return null;
				}
			});
		}
	}

	public CardStep getCardStep(String step) {
		initialize();
		return singleton.stream().filter(x -> x.getStep().equals(step)).findFirst().get();
	}

}