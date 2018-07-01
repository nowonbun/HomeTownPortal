package map;

import java.io.Serializable;
import javax.persistence.*;
import model.*;

@Entity
@Table(name = "MAP_ACTION_ROLE_USER")
@NamedQuery(name = "MapActionRoleUser.findAll", query = "SELECT m FROM MapActionRoleUser m")
public class MapActionRoleUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapActionRoleUserPK id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_CODE")
	private Role role;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;

	private MapActionRoleUser() {
	}

	public MapActionRoleUserPK getId() {
		return this.id;
	}

	public void setId(MapActionRoleUserPK id) {
		this.id = id;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}