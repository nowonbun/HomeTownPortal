package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the USERINFO database table.
 * 
 */
@Entity
@Table(name = "USERINFO")
@NamedQuery(name = "Userinfo.findAll", query = "SELECT u FROM Userinfo u")
public class Userinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "background_img")
	private String backgroundImg;

	private String givenName;

	private String img;

	private String name;

	private String nickname;

	// bi-directional many-to-one association to Cookieinfo
	@OneToMany(mappedBy = "userinfo")
	private List<Cookieinfo> cookieinfos;

	public Userinfo() {
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

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<Cookieinfo> getCookieinfos() {
		return this.cookieinfos;
	}

	public void setCookieinfos(List<Cookieinfo> cookieinfos) {
		this.cookieinfos = cookieinfos;
	}

	public Cookieinfo addCookieinfo(Cookieinfo cookieinfo) {
		getCookieinfos().add(cookieinfo);
		cookieinfo.setUserinfo(this);

		return cookieinfo;
	}

	public Cookieinfo removeCookieinfo(Cookieinfo cookieinfo) {
		getCookieinfos().remove(cookieinfo);
		cookieinfo.setUserinfo(null);

		return cookieinfo;
	}

}