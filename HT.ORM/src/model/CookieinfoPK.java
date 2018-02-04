package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the COOKIEINFO database table.
 * 
 */
@Embeddable
public class CookieinfoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="COOKIEKEY")
	private String cookiekey;

	@Column(name="ID", insertable=false, updatable=false)
	private String id;

	public CookieinfoPK() {
	}
	public String getCookiekey() {
		return this.cookiekey;
	}
	public void setCookiekey(String cookiekey) {
		this.cookiekey = cookiekey;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CookieinfoPK)) {
			return false;
		}
		CookieinfoPK castOther = (CookieinfoPK)other;
		return 
			this.cookiekey.equals(castOther.cookiekey)
			&& this.id.equals(castOther.id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cookiekey.hashCode();
		hash = hash * prime + this.id.hashCode();
		
		return hash;
	}
}