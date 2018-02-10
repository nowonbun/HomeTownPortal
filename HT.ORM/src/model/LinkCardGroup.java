package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the MAP_CARD_GROUP database table.
 * 
 */
@Entity
@Table(name = "MAP_CARD_GROUP")
@NamedQuery(name = "LinkCardGroup.findAll", query = "SELECT l FROM LinkCardGroup l")
public class LinkCardGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LinkCardGroupPK id;

	// bi-directional many-to-one association to Card
	@ManyToOne
	@JoinColumn(name = "group_idx")
	private Group mstGroup;

	@ManyToOne
	@JoinColumn(name = "card_idx")
	private Card tsnCard;

	public LinkCardGroup() {
	}

	public LinkCardGroupPK getId() {
		return this.id;
	}

	public void setId(LinkCardGroupPK id) {
		this.id = id;
	}

	public Card getTsnCard() {
		return this.tsnCard;
	}

	public void setTsnCard(Card tsnCard) {
		this.tsnCard = tsnCard;
	}

	public Group getMstGroup() {
		return mstGroup;
	}

	public void setMstGroup(Group mstGroup) {
		this.mstGroup = mstGroup;
	}

}