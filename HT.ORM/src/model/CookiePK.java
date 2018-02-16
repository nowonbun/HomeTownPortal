package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TSN_COOKIE database table.
 * 
 */
@Embeddable
public class CookiePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID", insertable=false, updatable=false)
	private String id;

	@Column(name="COOKIEKEY")
	private String cookiekey;

	public CookiePK() {
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCookiekey() {
		return this.cookiekey;
	}
	public void setCookiekey(String cookiekey) {
		this.cookiekey = cookiekey;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CookiePK)) {
			return false;
		}
		CookiePK castOther = (CookiePK)other;
		return 
			this.id.equals(castOther.id)
			&& this.cookiekey.equals(castOther.cookiekey);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.cookiekey.hashCode();
		
		return hash;
	}
}