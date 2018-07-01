package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="MST_LOOK_UP")
@NamedQuery(name="LookUp.findAll", query="SELECT m FROM LookUp m")
public class LookUp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="`KEY`")
	private String key;

	@Column(name="VALUE")
	private String value;

	public LookUp() {
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}