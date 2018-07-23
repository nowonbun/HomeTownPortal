package model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import java.util.Date;

@Entity
@Table(name = "TSN_STATE_INFO")
@NamedQuery(name = "StateInfo.findAll", query = "SELECT s FROM StateInfo s where s.isDelete = false")
@Cacheable(false)
@Cache(alwaysRefresh = true, isolation = CacheIsolationType.ISOLATED, size = 0, expiry = 0)
public class StateInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDX")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "CREATER")
	private String creater;

	@Column(name = "IS_DELETE")
	private boolean isDelete;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE")
	private Date lastUpdate;

	@Column(name = "LAST_UPDATER")
	private String lastUpdater;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE")
	private State state;

	@OneToOne(mappedBy = "stateInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Cookie cookie;

	@OneToOne(mappedBy = "stateInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User user;

	@OneToOne(mappedBy = "stateInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Company company;

	@OneToOne(mappedBy = "stateInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Group group;

	@OneToOne(mappedBy = "stateInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Password password;

	@OneToOne(mappedBy = "stateInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Comment comment;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public StateInfo() {
	}

	public int getIdx() {
		return this.idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdater() {
		return this.lastUpdater;
	}

	public void setLastUpdater(String lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Cookie getCookie() {
		return this.cookie;
	}

	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

}