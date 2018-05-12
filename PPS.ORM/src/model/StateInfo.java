package model;

import java.io.Serializable;
import javax.persistence.*;
import map.*;

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
	private Company company;
	
	@OneToOne(mappedBy = "stateInfo")
	private Group group;
	
	@OneToOne(mappedBy = "stateInfo")
	private Password password;
	
	@OneToOne(mappedBy = "stateInfo")
	private Comment comment;
	
	@OneToOne(mappedBy = "stateInfo")
	private MapActionRoleCompany mapActionRoleCompany;
	
	@OneToOne(mappedBy = "stateInfo")
	private MapActionRoleGroup mapActionRoleGroup;
	
	@OneToOne(mappedBy = "stateInfo")
	private MapActionRoleUser mapActionRoleUser;
	
	@OneToOne(mappedBy = "stateInfo")
	private MapViewRoleCompany mapViewRoleCompany;
	
	@OneToOne(mappedBy = "stateInfo")
	private MapViewRoleGroup mapViewRoleGroup;
	
	@OneToOne(mappedBy = "stateInfo")
	private MapViewRoleUser mapViewRoleUser;

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

	public MapActionRoleCompany getMapActionRoleCompany() {
		return mapActionRoleCompany;
	}

	public void setMapActionRoleCompany(MapActionRoleCompany mapActionRoleCompany) {
		this.mapActionRoleCompany = mapActionRoleCompany;
	}

	public MapActionRoleGroup getMapActionRoleGroup() {
		return mapActionRoleGroup;
	}

	public void setMapActionRoleGroup(MapActionRoleGroup mapActionRoleGroup) {
		this.mapActionRoleGroup = mapActionRoleGroup;
	}

	public MapActionRoleUser getMapActionRoleUser() {
		return mapActionRoleUser;
	}

	public void setMapActionRoleUser(MapActionRoleUser mapActionRoleUser) {
		this.mapActionRoleUser = mapActionRoleUser;
	}

	public MapViewRoleCompany getMapViewRoleCompany() {
		return mapViewRoleCompany;
	}

	public void setMapViewRoleCompany(MapViewRoleCompany mapViewRoleCompany) {
		this.mapViewRoleCompany = mapViewRoleCompany;
	}

	public MapViewRoleGroup getMapViewRoleGroup() {
		return mapViewRoleGroup;
	}

	public void setMapViewRoleGroup(MapViewRoleGroup mapViewRoleGroup) {
		this.mapViewRoleGroup = mapViewRoleGroup;
	}

	public MapViewRoleUser getMapViewRoleUser() {
		return mapViewRoleUser;
	}

	public void setMapViewRoleUser(MapViewRoleUser mapViewRoleUser) {
		this.mapViewRoleUser = mapViewRoleUser;
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