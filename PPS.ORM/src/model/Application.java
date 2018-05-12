package model;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;

@Entity
@Table(name = "TSN_APPLICATION")
@NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a")
public class Application extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApplicationPK id;

	@ManyToOne
	@JoinColumn(name = "ID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "COMMENT_IDX")
	private Comment comment;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
	private Application() {	}

	public Application(User user, String createUser) {
		ApplicationPK pk = new ApplicationPK();
		pk.setId(user.getId());
		this.setId(pk);
		this.setUser(user);
		super.createTransation(createUser);

	}

	public ApplicationPK getId() {
		return this.id;
	}

	public void setId(ApplicationPK id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment getComment() {
		return this.comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@Override
	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}

	@Override
	public StateInfo getStateInfo() {
		return this.stateInfo;
	}

}