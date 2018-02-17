package model;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;

import java.util.Date;

@Entity
@Table(name = "TSN_COOKIE")
@NamedQuery(name = "Cookie.findAll", query = "SELECT c FROM Cookie c where c.stateInfo.isDelete = false")
public class Cookie extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CookiePK id;

	@Column(name = "IPADDRESS")
	private String ipaddress;

	//@Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_CONNECT_DATE")
	private Date lastConnectDate;

	@ManyToOne
	@JoinColumn(name = "ID")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	public Cookie() {
		
	}
	public Cookie(String user) {
		super.create(user);
	}
	
	public CookiePK getId() {
		return this.id;
	}

	public void setId(CookiePK id) {
		this.id = id;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Date getLastConnectDate() {
		return this.lastConnectDate;
	}

	public void setLastConnectDate(Date lastConnectDate) {
		this.lastConnectDate = lastConnectDate;
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