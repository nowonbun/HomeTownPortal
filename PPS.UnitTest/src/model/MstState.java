package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MST_STATE database table.
 * 
 */
@Entity
@Table(name="MST_STATE")
@NamedQuery(name="MstState.findAll", query="SELECT m FROM MstState m")
public class MstState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="STATE")
	private int state;

	@Column(name="NAME")
	private String name;

	//bi-directional many-to-one association to TsnStateInfo
	@OneToMany(mappedBy="mstState")
	private List<TsnStateInfo> tsnStateInfos;

	public MstState() {
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

	public List<TsnStateInfo> getTsnStateInfos() {
		return this.tsnStateInfos;
	}

	public void setTsnStateInfos(List<TsnStateInfo> tsnStateInfos) {
		this.tsnStateInfos = tsnStateInfos;
	}

	public TsnStateInfo addTsnStateInfo(TsnStateInfo tsnStateInfo) {
		getTsnStateInfos().add(tsnStateInfo);
		tsnStateInfo.setMstState(this);

		return tsnStateInfo;
	}

	public TsnStateInfo removeTsnStateInfo(TsnStateInfo tsnStateInfo) {
		getTsnStateInfos().remove(tsnStateInfo);
		tsnStateInfo.setMstState(null);

		return tsnStateInfo;
	}

}