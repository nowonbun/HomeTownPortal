package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MST_GROUP database table.
 * 
 */
@Entity
@Table(name="MST_GROUP")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idx;

	private String authority;

	private String name;

	//bi-directional many-to-many association to Card
	@ManyToMany(mappedBy="mstGroups")
	private List<Card> tsnCards;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="mstGroup")
	private List<User> tsnUsers;

	public Group() {
	}

	public int getIdx() {
		return this.idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getTsnCards() {
		return this.tsnCards;
	}

	public void setTsnCards(List<Card> tsnCards) {
		this.tsnCards = tsnCards;
	}

	public List<User> getTsnUsers() {
		return this.tsnUsers;
	}

	public void setTsnUsers(List<User> tsnUsers) {
		this.tsnUsers = tsnUsers;
	}

	public User addTsnUser(User tsnUser) {
		getTsnUsers().add(tsnUser);
		tsnUser.setMstGroup(this);

		return tsnUser;
	}

	public User removeTsnUser(User tsnUser) {
		getTsnUsers().remove(tsnUser);
		tsnUser.setMstGroup(null);

		return tsnUser;
	}

}