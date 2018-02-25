package model;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;

import java.util.List;

@Entity
@Table(name = "TSN_USER")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u where u.stateInfo.isDelete = false")
public class User extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "BACKGROUND_IMG")
	private String backgroundImg;

	@Column(name = "GIVEN_NAME")
	private String givenName;

	@Column(name = "IMG")
	private String img;

	@Column(name = "NAME")
	private String name;

	@Column(name = "NICK_NAME")
	private String nickName;

	@OneToMany(mappedBy = "user")
	private List<Cookie> cookies;

	@OneToMany(mappedBy = "user")
	private List<Password> passwords;

	@ManyToOne
	@JoinColumn(name = "GROUP_CODE")
	private Group group;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	public User() {
		
	}

	public User(String user) {
		super.create(user);
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

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
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

	public Cookie addCookie(Cookie cookie) {
		getCookies().add(cookie);
		cookie.setUser(this);

		return cookie;
	}

	public Cookie removeCookie(Cookie cookie) {
		getCookies().remove(cookie);
		cookie.setUser(null);

		return cookie;
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

	public Password addPassword(Password password) {
		getPasswords().add(password);
		password.setUser(this);

		return password;
	}

	public Password removePassword(Password password) {
		getPasswords().remove(password);
		password.setUser(null);

		return password;
	}

	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}
}