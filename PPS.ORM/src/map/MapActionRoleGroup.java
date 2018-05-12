package map;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;
import model.*;

@Entity
@Table(name = "MAP_ACTION_ROLE_GROUP")
@NamedQuery(name = "MapActionRoleGroup.findAll", query = "SELECT m FROM MapActionRoleGroup m")
public class MapActionRoleGroup extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapActionRoleGroupPK id;

	@ManyToOne
	@JoinColumn(name = "ROLE_CODE")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	private Group group;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
	private MapActionRoleGroup() { }

	public MapActionRoleGroup(String user) {
		super.createTransation(user);
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

	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}

}