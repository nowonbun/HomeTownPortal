package map;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class MapViewRoleCompanyPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CARD_CODE", insertable=false, updatable=false)
	private String cardCode;

	@Column(name = "COMPANY_ID", insertable=false, updatable=false)
	private int companyId;

	public MapViewRoleCompanyPK() {
	}

	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
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
		if (!(other instanceof MapViewRoleCompanyPK)) {
			return false;
		}
		MapViewRoleCompanyPK castOther = (MapViewRoleCompanyPK) other;
		return this.cardCode.equals(castOther.cardCode) && (this.companyId == castOther.companyId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cardCode.hashCode();
		hash = hash * prime + this.companyId;

		return hash;
	}
}