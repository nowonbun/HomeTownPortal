package map;

import java.io.Serializable;
import javax.persistence.*;
import model.*;

@Entity
@Table(name = "MAP_ACTION_ROLE_GROUP")
@NamedQuery(name = "MapActionRoleGroup.findAll", query = "SELECT m FROM MapActionRoleGroup m")
public class MapActionRoleGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapActionRoleGroupPK id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_CODE")
	private Role role;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "GROUP_ID")
	private Group group;

	private MapActionRoleGroup() {
	}

	public MapActionRoleGroupPK getId() {
		return this.id;
	}

	public void setId(MapActionRoleGroupPK id) {
		this.id = id;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}