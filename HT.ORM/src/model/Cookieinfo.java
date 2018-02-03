package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the COOKIEINFO database table.
 * 
 */
@Entity
@Table(name = "COOKIEINFO")
@NamedQuery(name = "Cookieinfo.findAll", query = "SELECT c FROM Cookieinfo c")
public class Cookieinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CookieinfoPK id;

	@Temporal(TemporalType.DATE)
	private Date createdate;

	// bi-directional many-to-one association to Userinfo
	@ManyToOne
	@JoinColumn(name = "id")
	private Userinfo userinfo;

	public Cookieinfo() {
	}

	public CookieinfoPK getId() {
		return this.id;
	}

	public void setId(CookieinfoPK id) {
		this.id = id;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Userinfo getUserinfo() {
		return this.userinfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

}