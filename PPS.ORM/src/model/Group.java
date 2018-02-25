package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MST_GROUP")
@NamedQuery(name = "Group.findAll", query = "SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODE")
	private String code;

	@Column(name = "NAME")
	private String name;

	@ManyToMany
	@JoinTable(name = "MAP_CARD_GROUP", joinColumns = { @JoinColumn(name = "GROUP_CODE") }, inverseJoinColumns = {
			@JoinColumn(name = "CARD_CODE") })
	private List<Card> cards;

	@OneToMany(mappedBy = "group")
	private List<User> users;

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

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setGroup(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setGroup(null);

		return user;
	}
}