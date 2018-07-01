package map;

import java.io.Serializable;
import javax.persistence.*;
import model.*;

@Entity
@Table(name = "MAP_VIEW_ROLE_GROUP")
@NamedQuery(name = "MapViewRoleGroup.findAll", query = "SELECT m FROM MapViewRoleGroup m")
public class MapViewRoleGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MapViewRoleGroupPK id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CARD_CODE")
	private Card card;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "GROUP_ID")
	private Group group;

	private MapViewRoleGroup() {
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

}