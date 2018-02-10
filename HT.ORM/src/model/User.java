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
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="background_img")
	private String backgroundImg;

	private String givenName;

	private String img;

	private String name;

	private String nickname;

	//bi-directional many-to-one association to Cookie
	@OneToMany(mappedBy="tsnUser")
	private List<Cookie> tsnCookies;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="groupidx")
	private Group mstGroup;

	public User() {
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

	public List<Cookie> getTsnCookies() {
		return this.tsnCookies;
	}

	public void setTsnCookies(List<Cookie> tsnCookies) {
		this.tsnCookies = tsnCookies;
	}

	public Cookie addTsnCooky(Cookie tsnCooky) {
		getTsnCookies().add(tsnCooky);
		tsnCooky.setTsnUser(this);

		return tsnCooky;
	}

	public Cookie removeTsnCooky(Cookie tsnCooky) {
		getTsnCookies().remove(tsnCooky);
		tsnCooky.setTsnUser(null);

		return tsnCooky;
	}

	public Group getMstGroup() {
		return this.mstGroup;
	}

	public void setMstGroup(Group mstGroup) {
		this.mstGroup = mstGroup;
	}

}