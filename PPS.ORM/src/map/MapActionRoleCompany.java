package map;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import model.*;

@Entity
@Table(name = "MAP_ACTION_ROLE_COMPANY")
@NamedQuery(name = "MapActionRoleCompany.findAll", query = "SELECT m FROM MapActionRoleCompany m")
@Cacheable(false)
@Cache(alwaysRefresh = true, isolation = CacheIsolationType.ISOLATED, size = 0, expiry = 0)
public class MapActionRoleCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapActionRoleCompanyPK id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_CODE")
	private Role role;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	private MapActionRoleCompany() {
	}

	public MapActionRoleCompanyPK getId() {
		return this.id;
	}

	public void setId(MapActionRoleCompanyPK id) {
		this.id = id;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}