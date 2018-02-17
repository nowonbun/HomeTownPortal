package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the TSN_STATE_INFO database table.
 * 
 */
@Entity
@Table(name="TSN_STATE_INFO")
@NamedQuery(name="TsnStateInfo.findAll", query="SELECT t FROM TsnStateInfo t")
public class TsnStateInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="IDX")
	private int idx;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATER")
	private String creater;

	@Column(name="IS_DELETE")
	private Object isDelete;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPDATE")
	private Date lastUpdate;

	@Column(name="LAST_UPDATER")
	private String lastUpdater;

	//bi-directional many-to-one association to TsnCookie
	@OneToMany(mappedBy="tsnStateInfo")
	private List<TsnCookie> tsnCookies;

	//bi-directional many-to-one association to TsnPassword
	@OneToMany(mappedBy="tsnStateInfo")
	private List<TsnPassword> tsnPasswords;

	//bi-directional many-to-one association to MstState
	@ManyToOne
	@JoinColumn(name="STATE")
	private MstState mstState;

	//bi-directional many-to-one association to TsnUser
	@OneToMany(mappedBy="tsnStateInfo")
	private List<TsnUser> tsnUsers;

	public TsnStateInfo() {
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

	public Object getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Object isDelete) {
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

	public List<TsnCookie> getTsnCookies() {
		return this.tsnCookies;
	}

	public void setTsnCookies(List<TsnCookie> tsnCookies) {
		this.tsnCookies = tsnCookies;
	}

	public TsnCookie addTsnCooky(TsnCookie tsnCooky) {
		getTsnCookies().add(tsnCooky);
		tsnCooky.setTsnStateInfo(this);

		return tsnCooky;
	}

	public TsnCookie removeTsnCooky(TsnCookie tsnCooky) {
		getTsnCookies().remove(tsnCooky);
		tsnCooky.setTsnStateInfo(null);

		return tsnCooky;
	}

	public List<TsnPassword> getTsnPasswords() {
		return this.tsnPasswords;
	}

	public void setTsnPasswords(List<TsnPassword> tsnPasswords) {
		this.tsnPasswords = tsnPasswords;
	}

	public TsnPassword addTsnPassword(TsnPassword tsnPassword) {
		getTsnPasswords().add(tsnPassword);
		tsnPassword.setTsnStateInfo(this);

		return tsnPassword;
	}

	public TsnPassword removeTsnPassword(TsnPassword tsnPassword) {
		getTsnPasswords().remove(tsnPassword);
		tsnPassword.setTsnStateInfo(null);

		return tsnPassword;
	}

	public MstState getMstState() {
		return this.mstState;
	}

	public void setMstState(MstState mstState) {
		this.mstState = mstState;
	}

	public List<TsnUser> getTsnUsers() {
		return this.tsnUsers;
	}

	public void setTsnUsers(List<TsnUser> tsnUsers) {
		this.tsnUsers = tsnUsers;
	}

	public TsnUser addTsnUser(TsnUser tsnUser) {
		getTsnUsers().add(tsnUser);
		tsnUser.setTsnStateInfo(this);

		return tsnUser;
	}

	public TsnUser removeTsnUser(TsnUser tsnUser) {
		getTsnUsers().remove(tsnUser);
		tsnUser.setTsnStateInfo(null);

		return tsnUser;
	}

}