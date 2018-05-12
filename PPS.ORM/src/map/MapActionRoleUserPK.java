package map;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MAP_ACTION_ROLE_USER database table.
 * 
 */
@Embeddable
public class MapActionRoleUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ROLE_CODE", insertable=false, updatable=false)
	private String roleCode;

	@Column(name="USER_ID", insertable=false, updatable=false)
	private String userId;

	public MapActionRoleUserPK() {
	}
	public String getRoleCode() {
		return this.roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MapActionRoleUserPK)) {
			return false;
		}
		MapActionRoleUserPK castOther = (MapActionRoleUserPK)other;
		return 
			this.roleCode.equals(castOther.roleCode)
			&& this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.roleCode.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}
}