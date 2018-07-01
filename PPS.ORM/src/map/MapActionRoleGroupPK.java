package map;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class MapActionRoleGroupPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ROLE_CODE", insertable=false, updatable=false)
	private String roleCode;

	@Column(name = "GROUP_ID", insertable=false, updatable=false)
	private int groupId;

	public MapActionRoleGroupPK() {
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MapActionRoleGroupPK)) {
			return false;
		}
		MapActionRoleGroupPK castOther = (MapActionRoleGroupPK) other;
		return this.roleCode.equals(castOther.roleCode) && (this.groupId == castOther.groupId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.roleCode.hashCode();
		hash = hash * prime + this.groupId;

		return hash;
	}
}