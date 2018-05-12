package map;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MAP_VIEW_ROLE_USER database table.
 * 
 */
@Embeddable
public class MapViewRoleUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CARD_CODE", insertable=false, updatable=false)
	private String cardCode;

	@Column(name="USER_ID", insertable=false, updatable=false)
	private String userId;

	public MapViewRoleUserPK() {
	}
	public String getCardCode() {
		return this.cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
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
		if (!(other instanceof MapViewRoleUserPK)) {
			return false;
		}
		MapViewRoleUserPK castOther = (MapViewRoleUserPK)other;
		return 
			this.cardCode.equals(castOther.cardCode)
			&& this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cardCode.hashCode();
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}
}