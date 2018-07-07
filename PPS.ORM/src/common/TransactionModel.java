package common;

import java.util.ArrayList;
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
		List<T> ret = new ArrayList<>();
		for (T entity : func.run((R) this)) {
			ret.add(entity);
		}
		return ret;
	}

	public abstract void setStateInfo(StateInfo stateInfo);

	public abstract StateInfo getStateInfo();
}
