package model;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class ApplicationPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "IDX", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;

	@Column(name = "ID", insertable = false, updatable = false, nullable = false)
	private String id;

	public ApplicationPK() {
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
		if (!(other instanceof ApplicationPK)) {
			return false;
		}
		ApplicationPK castOther = (ApplicationPK) other;
		return (this.idx == castOther.idx) && this.id.equals(castOther.id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idx;
		hash = hash * prime + this.id.hashCode();

		return hash;
	}
}