package map;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class MapActionRoleCompanyPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ROLE_CODE", insertable=false, updatable=false)
	private String roleCode;

	@Column(name = "COMPANY_ID", insertable=false, updatable=false)
	private int companyId;

	public MapActionRoleCompanyPK() {
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public int getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MapActionRoleCompanyPK)) {
			return false;
		}
		MapActionRoleCompanyPK castOther = (MapActionRoleCompanyPK) other;
		return this.roleCode.equals(castOther.roleCode) && (this.companyId == castOther.companyId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.roleCode.hashCode();
		hash = hash * prime + this.companyId;

		return hash;
	}
}