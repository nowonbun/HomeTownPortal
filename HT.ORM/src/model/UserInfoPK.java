package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the UserInfo database table.
 * 
 */
@Embeddable
public class UserInfoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idx;

	private String id;

	public UserInfoPK() {
	}
	public int getIdx() {
		return this.idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
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
		if (!(other instanceof UserInfoPK)) {
			return false;
		}
		UserInfoPK castOther = (UserInfoPK)other;
		return 
			(this.idx == castOther.idx)
			&& this.id.equals(castOther.id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idx;
		hash = hash * prime + this.id.hashCode();
		
		return hash;
	}
}