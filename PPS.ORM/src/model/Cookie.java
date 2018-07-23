package model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import common.TransactionModel;

import java.util.Date;

@Entity
@Table(name = "TSN_COOKIE")
@NamedQuery(name = "Cookie.findAll", query = "SELECT c FROM Cookie c where c.stateInfo.isDelete = false")
@Cacheable(false)
@Cache(alwaysRefresh = true, isolation = CacheIsolationType.ISOLATED, size = 0, expiry = 0)
public class Cookie extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CookiePK id;

	@Column(name = "IPADDRESS")
	private String ipaddress;

	// @Temporal(TemporalType.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_CONNECT_DATE")
	private Date lastConnectDate;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID")
	private User user;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
	private Cookie() {
	}

	public Cookie(User user, String cookiekey, String createUser) {
		CookiePK pk = new CookiePK();
		pk.setId(user.getId());
		pk.setCookiekey(cookiekey);
		this.setId(pk);
		this.setUser(user);
		super.createTransation(createUser);
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