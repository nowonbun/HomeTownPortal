package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TSN_USER database table.
 * 
 */
@Entity
@Table(name="TSN_USER")
@NamedQuery(name="TsnUser.findAll", query="SELECT t FROM TsnUser t")
public class TsnUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;

	@Column(name="BACKGROUND_IMG")
	private String backgroundImg;

	@Column(name="GIVEN_NAME")
	private String givenName;

	@Column(name="IMG")
	private String img;

	@Column(name="NAME")
	private String name;

	@Column(name="NICK_NAME")
	private String nickName;

	//bi-directional many-to-one association to TsnCookie
	@OneToMany(mappedBy="tsnUser")
	private List<TsnCookie> tsnCookies;

	//bi-directional many-to-one association to TsnPassword
	@OneToMany(mappedBy="tsnUser")
	private List<TsnPassword> tsnPasswords;

	//bi-directional many-to-one association to MstGroup
	@ManyToOne
	@JoinColumn(name="GROUP_CODE")
	private MstGroup mstGroup;

	//bi-directional many-to-one association to TsnStateInfo
	@ManyToOne
	@JoinColumn(name="STATE")
	private TsnStateInfo tsnStateInfo;

	public TsnUser() {
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

	public List<TsnCookie> getTsnCookies() {
		return this.tsnCookies;
	}

	public void setTsnCookies(List<TsnCookie> tsnCookies) {
		this.tsnCookies = tsnCookies;
	}

	public TsnCookie addTsnCooky(TsnCookie tsnCooky) {
		getTsnCookies().add(tsnCooky);
		tsnCooky.setTsnUser(this);

		return tsnCooky;
	}

	public TsnCookie removeTsnCooky(TsnCookie tsnCooky) {
		getTsnCookies().remove(tsnCooky);
		tsnCooky.setTsnUser(null);

		return tsnCooky;
	}

	public List<TsnPassword> getTsnPasswords() {
		return this.tsnPasswords;
	}

	public void setTsnPasswords(List<TsnPassword> tsnPasswords) {
		this.tsnPasswords = tsnPasswords;
	}

	public TsnPassword addTsnPassword(TsnPassword tsnPassword) {
		getTsnPasswords().add(tsnPassword);
		tsnPassword.setTsnUser(this);

		return tsnPassword;
	}

	public TsnPassword removeTsnPassword(TsnPassword tsnPassword) {
		getTsnPasswords().remove(tsnPassword);
		tsnPassword.setTsnUser(null);

		return tsnPassword;
	}

	public MstGroup getMstGroup() {
		return this.mstGroup;
	}

	public void setMstGroup(MstGroup mstGroup) {
		this.mstGroup = mstGroup;
	}

	public TsnStateInfo getTsnStateInfo() {
		return this.tsnStateInfo;
	}

	public void setTsnStateInfo(TsnStateInfo tsnStateInfo) {
		this.tsnStateInfo = tsnStateInfo;
	}

}