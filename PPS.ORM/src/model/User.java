package model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import common.TransactionModel;
import java.util.List;

@Entity
@Table(name = "TSN_USER")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u where u.stateInfo.isDelete = false")
@Cacheable(false)
@Cache(alwaysRefresh = true, isolation = CacheIsolationType.ISOLATED, size = 0, expiry = 0)
public class User extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "BACKGROUND_IMG")
	private String backgroundImg;

	@Column(name = "GIVEN_NAME")
	private String givenName;

	@Lob
	@Column(name = "IMG_BLOB")
	private byte[] imgBlob;

	@Column(name = "NAME")
	private String name;

	@Column(name = "NICK_NAME")
	private String nickName;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Cookie> cookies;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Password> passwords;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	private Group group;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "MAP_VIEW_ROLE_USER", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "CARD_CODE") })
	private List<Card> cards;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "MAP_ACTION_ROLE_USER", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_CODE") })
	private List<Role> roles;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
	private User() {
	}

	public User(String user, State type) {
		super.createTransation(user);
		this.stateInfo.setState(type);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBackgroundImg() {
		return this.backgroundImg;
	}

	public void setBackgroundImg(String backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public String getGivenName() {
		return this.givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public byte[] getImgBlob() {
		return this.imgBlob;
	}

	public void setImgBlob(byte[] imgBlob) {
		this.imgBlob = imgBlob;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<Cookie> getCookies() {
		return this.cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Password> getPasswords() {
		return passwords;
	}

	public void setPasswords(List<Password> passwords) {
		this.passwords = passwords;
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