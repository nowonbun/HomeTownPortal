package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MST_STATE")
@NamedQuery(name = "State.findAll", query = "SELECT s FROM State s")
public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STATE")
	private int state;

	@Column(name = "NAME")
	private String name;

	@OneToMany(mappedBy = "state")
	private List<StateInfo> stateInfos;

	public State() {
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StateInfo> getStateInfos() {
		return this.stateInfos;
	}

	public void setStateInfos(List<StateInfo> stateInfos) {
		this.stateInfos = stateInfos;
	}

	public StateInfo addStateInfo(StateInfo stateInfo) {
		getStateInfos().add(stateInfo);
		stateInfo.setState(this);

		return stateInfo;
	}

	public StateInfo removeStateInfo(StateInfo stateInfo) {
		getStateInfos().remove(stateInfo);
		stateInfo.setState(null);

		return stateInfo;
	}

}