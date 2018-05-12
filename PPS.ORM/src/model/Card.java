package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "MST_CARD")
@NamedQuery(name = "Card.findAll", query = "SELECT c FROM Card c")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODE")
	private String code;

	@Column(name = "COLOR")
	private String color;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "HREF")
	private String href;

	@Column(name = "ICON")
	private String icon;

	@Lob
	@Column(name = "IMG")
	private byte[] img;

	@Column(name = "NAME")
	private String name;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "ORDER_SEQ")
	private int orderSeq;

	@ManyToMany
	@JoinTable(	name = "MAP_VIEW_ROLE_COMPANY", 
				joinColumns = { @JoinColumn(name = "CARD_CODE") }, 
				inverseJoinColumns = { @JoinColumn(name = "COMPANY_ID") })
	private List<Company> companies;

	@ManyToMany
	@JoinTable(	name = "MAP_VIEW_ROLE_GROUP", 
				joinColumns = { @JoinColumn(name = "CARD_CODE") }, 
				inverseJoinColumns = {	@JoinColumn(name = "GROUP_ID") })
	private List<Group> groups;

	@ManyToMany
	@JoinTable(	name = "MAP_VIEW_ROLE_USER", 
				joinColumns = { @JoinColumn(name = "CARD_CODE") }, 
				inverseJoinColumns = {	@JoinColumn(name = "USER_ID") })
	private List<User> users;

	@ManyToOne
	@JoinColumn(name = "STEP")
	private CardStep cardStep;

	@ManyToOne
	@JoinColumn(name = "CARD_TYPE")
	private CardType cardType;

	public Card() { }

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public byte[] getImg() {
		return this.img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrderSeq() {
		return this.orderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	public CardStep getCardStep() {
		return this.cardStep;
	}

	public void setCardStep(CardStep cardStep) {
		this.cardStep = cardStep;
	}

	public CardType getCardType() {
		return this.cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public List<Company> getCompanies() {
		return this.companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}