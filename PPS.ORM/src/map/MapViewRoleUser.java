package map;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import model.*;

@Entity
@Table(name = "MAP_VIEW_ROLE_USER")
@NamedQuery(name = "MapViewRoleUser.findAll", query = "SELECT m FROM MapViewRoleUser m")
@Cacheable(false)
@Cache(alwaysRefresh = true, isolation = CacheIsolationType.ISOLATED, size = 0, expiry = 0)
public class MapViewRoleUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapViewRoleUserPK id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CARD_CODE")
	private Card card;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;

	private MapViewRoleUser() {
	}

	public MapViewRoleUserPK getId() {
		return this.id;
	}

	public void setId(MapViewRoleUserPK id) {
		this.id = id;
	}

	public Card getCard() {
		return this.card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}