package model;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;

@Entity
@Table(name = "TSN_PASSWORD")
@NamedQuery(name = "Password.findAll", query = "SELECT p FROM Password p where p.stateInfo.isDelete = false")
public class Password extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PasswordPK id;

	@Column(name = "PASSWORD")
	private String password;

	@ManyToOne
	@JoinColumn(name = "ID")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	public Password() {

	}
	public Password(String user) {
		super.create(user);
	}

	public PasswordPK getId() {
		return this.id;
	}

	public void setId(PasswordPK id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StateInfo getStateInfo() {
		return this.stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}

}