package map;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import model.*;

@Entity
@Table(name = "MAP_VIEW_ROLE_COMPANY")
@NamedQuery(name = "MapViewRoleCompany.findAll", query = "SELECT m FROM MapViewRoleCompany m")
@Cacheable(false)
@Cache(alwaysRefresh = true, isolation = CacheIsolationType.ISOLATED, size = 0, expiry = 0)
public class MapViewRoleCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapViewRoleCompanyPK id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CARD_CODE")
	private Card card;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	private MapViewRoleCompany() {
	}

	public MapViewRoleCompanyPK getId() {
		return this.id;
	}

	public void setId(MapViewRoleCompanyPK id) {
		this.id = id;
	}

	public Card getCard() {
		return this.card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}