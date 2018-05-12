package map;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;
import model.*;

@Entity
@Table(name = "MAP_ACTION_ROLE_COMPANY")
@NamedQuery(name = "MapActionRoleCompany.findAll", query = "SELECT m FROM MapActionRoleCompany m")
public class MapActionRoleCompany extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapActionRoleCompanyPK id;

	@ManyToOne
	@JoinColumn(name = "ROLE_CODE")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
	private MapActionRoleCompany() { }

	public MapActionRoleCompany(String user) {
		super.createTransation(user);
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

	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}
}