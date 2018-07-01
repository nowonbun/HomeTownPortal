package map;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class MapViewRoleGroupPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CARD_CODE", insertable=false, updatable=false)
	private String cardCode;

	@Column(name="GROUP_ID", insertable=false, updatable=false)
	private int groupId;

	public MapViewRoleGroupPK() {
	}
	public String getCardCode() {
		return this.cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
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
		if (!(other instanceof MapViewRoleGroupPK)) {
			return false;
		}
		MapViewRoleGroupPK castOther = (MapViewRoleGroupPK)other;
		return 
			this.cardCode.equals(castOther.cardCode)
			&& (this.groupId == castOther.groupId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cardCode.hashCode();
		hash = hash * prime + this.groupId;
		
		return hash;
	}
}