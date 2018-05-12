package map;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;
import model.*;

@Entity
@Table(name="MAP_ACTION_ROLE_USER")
@NamedQuery(name="MapActionRoleUser.findAll", query="SELECT m FROM MapActionRoleUser m")
public class MapActionRoleUser extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapActionRoleUserPK id;

	@ManyToOne
	@JoinColumn(name = "ROLE_CODE")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
	private MapActionRoleUser() { }

	public MapActionRoleUser(String user) {
		super.createTransation(user);
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

	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}
}