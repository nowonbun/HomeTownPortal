package model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import common.TransactionModel;

@Entity
@Table(name = "TSN_COMMENT")
@NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c")
@Cacheable(false)
@Cache(alwaysRefresh = true, isolation = CacheIsolationType.ISOLATED, size = 0, expiry = 0)
public class Comment extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDX")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;

	@Lob
	@Column(name = "`COMMENT`")
	private String comment;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
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

	@Override
	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}

	@Override
	public StateInfo getStateInfo() {
		return this.stateInfo;
	}

}