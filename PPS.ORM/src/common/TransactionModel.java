package common;

import java.util.Date;
import java.util.List;
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

	@SuppressWarnings("unchecked")
	public <R, T> List<T> getLazyData(LambdaExpression<R, List<T>> func) {
		return DBUtil.getLazyData((R) this, func);
	}

	public abstract void setStateInfo(StateInfo stateInfo);

	public abstract StateInfo getStateInfo();
}
