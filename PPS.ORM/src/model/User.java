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

	@Column(name = "IMG_URL")
	private String imgUrl;

	@Lob
	@Column(name = "IMG_BLOB")
	private byte[] imgBlob;

	@Column(name = "NAME")
	private String name;

	@Column(name = "NICK_NAME")
	private String nickName;

	@OneToMany(mappedBy = "user")
	private List<Cookie> cookies;

	@OneToMany(mappedBy = "user")
	private List<Password> passwords;

	@OneToMany(mappedBy = "user")
	private List<Application> applications;

	@ManyToOne
	@JoinColumn(name = "GROUP_CODE")
	private Group group;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	private User() {

	}

	public User(String user) {
		super.createTransation(user);
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

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public Application addApplication(Application application) {
		getApplications().add(application);
		application.setUser(this);

		return application;
	}

	public Application removeApplication(Application application) {
		getApplications().remove(application);
		application.setUser(null);

		return application;
	}

	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}
}