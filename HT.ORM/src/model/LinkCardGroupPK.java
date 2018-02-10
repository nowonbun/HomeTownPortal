package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the MAP_CARD_GROUP database table.
 * 
 */
@Embeddable
public class LinkCardGroupPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="group_idx", insertable=false, updatable=false)
	private int groupIdx;

	@Column(name="card_idx", insertable=false, updatable=false)
	private int cardIdx;

	public LinkCardGroupPK() {
	}
	public int getGroupIdx() {
		return this.groupIdx;
	}
	public void setGroupIdx(int groupIdx) {
		this.groupIdx = groupIdx;
	}
	public int getCardIdx() {
		return this.cardIdx;
	}
	public void setCardIdx(int cardIdx) {
		this.cardIdx = cardIdx;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LinkCardGroupPK)) {
			return false;
		}
		LinkCardGroupPK castOther = (LinkCardGroupPK)other;
		return 
			(this.groupIdx == castOther.groupIdx)
			&& (this.cardIdx == castOther.cardIdx);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.groupIdx;
		hash = hash * prime + this.cardIdx;
		
		return hash;
	}
}