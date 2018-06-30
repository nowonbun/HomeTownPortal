package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import common.TransactionModel;

@Entity
@Table(name = "TSN_GROUP")
@NamedQuery(name = "Group.findAll", query = "SELECT g FROM Group g")
public class Group extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "MAP_VIEW_ROLE_GROUP", joinColumns = { @JoinColumn(name = "GROUP_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "CARD_CODE") })
	private List<Card> cards;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "MAP_ACTION_ROLE_GROUP", joinColumns = { @JoinColumn(name = "GROUP_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_CODE") })
	private List<Role> roles;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
	private Group() {
	}

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

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}