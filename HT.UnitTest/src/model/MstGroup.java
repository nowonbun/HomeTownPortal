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
@NamedQuery(name="MstGroup.findAll", query="SELECT m FROM MstGroup m")
public class MstGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODE")
	private String code;

	@Column(name="NAME")
	private String name;

	//bi-directional many-to-many association to MstCard
	@ManyToMany(mappedBy="mstGroups")
	private List<MstCard> mstCards;

	//bi-directional many-to-one association to TsnUser
	@OneToMany(mappedBy="mstGroup")
	private List<TsnUser> tsnUsers;

	public MstGroup() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MstCard> getMstCards() {
		return this.mstCards;
	}

	public void setMstCards(List<MstCard> mstCards) {
		this.mstCards = mstCards;
	}

	public List<TsnUser> getTsnUsers() {
		return this.tsnUsers;
	}

	public void setTsnUsers(List<TsnUser> tsnUsers) {
		this.tsnUsers = tsnUsers;
	}

	public TsnUser addTsnUser(TsnUser tsnUser) {
		getTsnUsers().add(tsnUser);
		tsnUser.setMstGroup(this);

		return tsnUser;
	}

	public TsnUser removeTsnUser(TsnUser tsnUser) {
		getTsnUsers().remove(tsnUser);
		tsnUser.setMstGroup(null);

		return tsnUser;
	}

}