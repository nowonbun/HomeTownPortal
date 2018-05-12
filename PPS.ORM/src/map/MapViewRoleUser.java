package map;

import java.io.Serializable;
import javax.persistence.*;

import common.TransactionModel;
import model.*;

@Entity
@Table(name = "MAP_VIEW_ROLE_USER")
@NamedQuery(name = "MapViewRoleUser.findAll", query = "SELECT m FROM MapViewRoleUser m")
public class MapViewRoleUser extends TransactionModel implements Serializable{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapViewRoleUserPK id;

	@ManyToOne
	@JoinColumn(name = "CARD_CODE")
	private Card card;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STATE")
	private StateInfo stateInfo;

	@SuppressWarnings("unused")
	private MapViewRoleUser() { }

	public MapViewRoleUser(String user) {
		super.createTransation(user);
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

	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}
}