package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TSN_COOKIE database table.
 * 
 */
@Entity
@Table(name="TSN_COOKIE")
@NamedQuery(name="TsnCookie.findAll", query="SELECT t FROM TsnCookie t")
public class TsnCookie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TsnCookiePK id;

	@Column(name="IPADDRESS")
	private String ipaddress;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_CONNECT_DATE")
	private Date lastConnectDate;

	//bi-directional many-to-one association to TsnUser
	@ManyToOne
	@JoinColumn(name="ID")
	private TsnUser tsnUser;

	//bi-directional many-to-one association to TsnStateInfo
	@ManyToOne
	@JoinColumn(name="STATE")
	private TsnStateInfo tsnStateInfo;

	public TsnCookie() {
	}

	public TsnCookiePK getId() {
		return this.id;
	}

	public void setId(TsnCookiePK id) {
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

	public TsnUser getTsnUser() {
		return this.tsnUser;
	}

	public void setTsnUser(TsnUser tsnUser) {
		this.tsnUser = tsnUser;
	}

	public TsnStateInfo getTsnStateInfo() {
		return this.tsnStateInfo;
	}

	public void setTsnStateInfo(TsnStateInfo tsnStateInfo) {
		this.tsnStateInfo = tsnStateInfo;
	}

}