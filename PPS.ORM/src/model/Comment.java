package model;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;

import java.util.List;

@Entity
@Table(name = "TSN_COMMENT")
@NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c")
public class Comment extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDX")
	private int idx;

	@Lob
	@Column(name = "`COMMENT`")
	private String comment;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@OneToMany(mappedBy = "comment")
	private List<Application> applications;

	private Comment() {
	}

	public Comment(String createUser) {
		super.createTransation(createUser);
	}

	public int getIdx() {
		return this.idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public Application addApplication(Application application) {
		getApplications().add(application);
		application.setComment(this);

		return application;
	}

	public Application removeTsnApplication(Application application) {
		getApplications().remove(application);
		application.setComment(null);

		return application;
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