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
	@Column(name="CODE")
	private String code;

	@Column(name="NAME")
	private String name;

	@ManyToMany(mappedBy="groups")
	private List<Card> cards;

	@OneToOne(mappedBy="group")
	private User user;

	public Group() {
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

	public List<Card> getCards() {
		return this.cards;
	}

	public void setMstCards(List<Card> cards) {
		this.cards = cards;
	}

	public User getUser() {
		return this.user;
	}

	public void setTsnUsers(User user) {
		this.user = user;
	}
}