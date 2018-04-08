package common;

import java.util.Date;

import model.StateInfo;

public abstract class TransactionModel {
	public void createTransation(String user) {
		StateInfo state = new StateInfo();
		state.setCreater(user);
		state.setCreateDate(new Date());
		state.setIsDelete(false);

		this.setStateInfo(state);
	}

	public void updateTransation(String user) {
		this.getStateInfo().setLastUpdater(user);
		this.getStateInfo().setLastUpdate(new Date());
	}

	public abstract void setStateInfo(StateInfo stateInfo);

	public abstract StateInfo getStateInfo();
}
