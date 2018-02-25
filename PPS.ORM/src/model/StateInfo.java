package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TSN_STATE_INFO")
@NamedQuery(name = "StateInfo.findAll", query = "SELECT s FROM StateInfo s where s.isDelete = false")
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

	@ManyToOne
	@JoinColumn(name = "STATE")
	private State state;

	@OneToOne(mappedBy = "stateInfo")
	private Cookie cookie;

	@OneToOne(mappedBy = "stateInfo")
	private User user;

	@OneToOne(mappedBy = "stateInfo")
	private Password password;

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

}