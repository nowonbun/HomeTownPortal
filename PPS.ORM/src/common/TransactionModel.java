package common;

import java.util.Date;

import model.StateInfo;

public abstract class TransactionModel {
	public void create(String user) {
		StateInfo state = new StateInfo();
		state.setCreater(user);
		state.setCreateDate(new Date());
		state.setIsDelete(false);

		this.setStateInfo(state);
	}

	public void update(String user) {
		this.getStateInfo().setLastUpdater(user);
		this.getStateInfo().setLastUpdate(new Date());
	}

	public abstract void setStateInfo(StateInfo info);

	public abstract StateInfo getStateInfo();
}
