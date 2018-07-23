package model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

@Entity
@Table(name="MST_LOOK_UP")
@NamedQuery(name="LookUp.findAll", query="SELECT m FROM LookUp m")
@Cacheable(false)
@Cache(alwaysRefresh = true, isolation = CacheIsolationType.ISOLATED, size = 0, expiry = 0)
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