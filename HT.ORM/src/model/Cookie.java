package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TSN_COOKIE")
@NamedQuery(name = "Cookie.findAll", query = "SELECT c FROM Cookie c")
public class Cookie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CookiePK id;

	@Column(name = "IPADDRESS")
	private String ipaddress;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_CONNECT_DATE")
	private Date lastConnectDate;

	@ManyToOne
	@JoinColumn(name = "ID")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private State state;

	public Cookie() {
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

	public State getTsnState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

}