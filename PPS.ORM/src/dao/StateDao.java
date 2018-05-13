package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import common.Manager;
import common.MasterDao;
import model.State;

public class StateDao extends MasterDao<State> {

	protected StateDao() {
		super(State.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<State> getDataList() {
		return Manager.transaction(() -> {
			try {
				Query query = Manager.get().createNamedQuery("State.findAll", State.class);
				return (List<State>) query.getResultList();
			} catch (NoResultException e) {
				return null;
			}
		});
	}

	public State getState(int state) {
		return getData().stream().filter(x -> x.getState() == state).findFirst().get();
	}
}