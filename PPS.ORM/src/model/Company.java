package model;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;

import java.util.List;

@Entity
@Table(name="TSN_COMPANY")
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private int id;

	@Column(name="NAME")
	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@OneToMany(mappedBy="company")
	private List<Group> groups;
	
	@ManyToMany(mappedBy="companies")
	private List<Card> cards;

	@SuppressWarnings("unused")
	private Company() { }

	public Company(String user) {
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

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Group addGroup(Group group) {
		getGroups().add(group);
		group.setCompany(this);

		return group;
	}

	public Group removeGroup(Group group) {
		getGroups().remove(group);
		group.setCompany(null);

		return group;
	}
	
	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}
}