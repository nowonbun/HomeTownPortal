package model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import java.util.List;

@Entity
@Table(name = "MST_STATE")
@NamedQuery(name = "State.findAll", query = "SELECT s FROM State s")
@Cacheable(false)
@Cache(alwaysRefresh = true, isolation = CacheIsolationType.ISOLATED, size = 0, expiry = 0)
public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STATE")
	private int state;

	@Column(name = "NAME")
	private String name;

	@OneToMany(mappedBy = "state", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
	
}