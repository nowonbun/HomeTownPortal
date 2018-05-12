package map;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;
import model.*;


@Entity
@Table(name="MAP_VIEW_ROLE_COMPANY")
@NamedQuery(name="MapViewRoleCompany.findAll", query="SELECT m FROM MapViewRoleCompany m")
public class MapViewRoleCompany extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapViewRoleCompanyPK id;

	@ManyToOne
	@JoinColumn(name="CARD_CODE")
	private Card card;

	@ManyToOne
	@JoinColumn(name="COMPANY_ID")
	private Company company;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;
	
	@SuppressWarnings("unused")
	private MapViewRoleCompany() { }

	public MapViewRoleCompany(String user) {
		super.createTransation(user);
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

	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}
}