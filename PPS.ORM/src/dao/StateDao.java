package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import common.Dao;
import common.Manager;
import model.State;

public class StateDao extends Dao<State> {

	private static List<State> singleton = null;

	protected StateDao() {
		super(State.class);
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		if (singleton == null) {
			singleton = Manager.transaction(() -> {
				try {
					Query query = Manager.get().createNamedQuery("State.findAll", State.class);
					return (List<State>) query.getResultList();
				} catch (NoResultException e) {
					return null;
				}
			});
		}
	}

	public State getState(int state) {
		initialize();
		return singleton.stream().filter(x -> x.getState() == state).findFirst().get();
	}
}