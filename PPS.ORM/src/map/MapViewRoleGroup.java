package map;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;
import model.*;

@Entity
@Table(name = "MAP_VIEW_ROLE_GROUP")
@NamedQuery(name = "MapViewRoleGroup.findAll", query = "SELECT m FROM MapViewRoleGroup m")
public class MapViewRoleGroup extends TransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapViewRoleGroupPK id;

	@ManyToOne
	@JoinColumn(name = "CARD_CODE")
	private Card card;

	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	private Group group;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;
	
	@SuppressWarnings("unused")
	private MapViewRoleGroup() { }

	public MapViewRoleGroup(String user) {
		super.createTransation(user);
	}


	public MapViewRoleGroupPK getId() {
		return this.id;
	}

	public void setId(MapViewRoleGroupPK id) {
		this.id = id;
	}

	public Card getCard() {
		return this.card;
	}

	public void setCard(Card card) {
		this.card = card;
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