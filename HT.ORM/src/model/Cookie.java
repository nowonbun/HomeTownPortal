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
@NamedQuery(name="Cookie.findAll", query="SELECT c FROM Cookie c")
public class Cookie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CookiePK id;

	@Temporal(TemporalType.DATE)
	private Date createdate;

	private String ipaddress;

	@Temporal(TemporalType.DATE)
	private Date lastconnecteddate;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="id")
	private User tsnUser;

	public Cookie() {
	}

	public CookiePK getId() {
		return this.id;
	}

	public void setId(CookiePK id) {
		this.id = id;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Date getLastconnecteddate() {
		return this.lastconnecteddate;
	}

	public void setLastconnecteddate(Date lastconnecteddate) {
		this.lastconnecteddate = lastconnecteddate;
	}

	public User getTsnUser() {
		return this.tsnUser;
	}

	public void setTsnUser(User tsnUser) {
		this.tsnUser = tsnUser;
	}

}