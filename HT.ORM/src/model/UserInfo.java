package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the UserInfo database table.
 * 
 */
@Entity
@NamedQuery(name="UserInfo.findAll", query="SELECT u FROM UserInfo u")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserInfoPK id;

	@Column(name="background_img")
	private String backgroundImg;

	@Column(name="cookie_key")
	private String cookieKey;

	private String givenName;

	private String img;

	private String name;

	private String nickname;

	public UserInfo() {
	}

	public UserInfoPK getId() {
		return this.id;
	}

	public void setId(UserInfoPK id) {
		this.id = id;
	}

	public String getBackgroundImg() {
		return this.backgroundImg;
	}

	public void setBackgroundImg(String backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public String getCookieKey() {
		return this.cookieKey;
	}

	public void setCookieKey(String cookieKey) {
		this.cookieKey = cookieKey;
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

}