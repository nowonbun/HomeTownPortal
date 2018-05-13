package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.Manager;
import common.MasterDao;
import model.CardStep;

public class CardStepDao extends MasterDao<CardStep> {

	protected CardStepDao() {
		super(CardStep.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<CardStep> getDataList() {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get().createNamedQuery("CardStep.findAll", CardStep.class);
				return (List<CardStep>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public CardStep getCardStep(String step) {
		return getData().stream().filter(x -> x.getStep().equals(step)).findFirst().get();
	}

}