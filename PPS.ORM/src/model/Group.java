package model;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;

@Entity
@Table(name="TSN_GROUP")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private int id;

	@Column(name="NAME")
	private String name;

	@ManyToOne
	@JoinColumn(name="COMPANY_ID")
	private Company company;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
	private Group() { }

	public Group(String user) {
		super.createTransation(user);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}
}