package model;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;

import java.util.List;

@Entity
@Table(name = "TSN_COMPANY")
@NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c")
public class Company extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "NAME")
	private String name;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Group> groups;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "MAP_VIEW_ROLE_COMPANY", joinColumns = {
			@JoinColumn(name = "COMPANY_ID") }, inverseJoinColumns = { @JoinColumn(name = "CARD_CODE") })
	private List<Card> cards;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "MAP_ACTION_ROLE_COMPANY", joinColumns = {
			@JoinColumn(name = "COMPANY_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_CODE") })
	private List<Role> roles;

	@SuppressWarnings("unused")
	private Company() {
	}

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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}