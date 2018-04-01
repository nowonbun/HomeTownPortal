package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "MST_CARD_GROUP")
@NamedQuery(name = "CardGroup.findAll", query = "SELECT m FROM CardGroup m")
public class CardGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String MANAGEMENT = "MNGM";
	public static String GUEST_APPLICATION = "GTAP";

	@Id
	@Column(name = "CARD_GROUP")
	private String cardGroup;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ORDER_SEQ")
	private int orderSeq;

	@OneToMany(mappedBy = "cardGroup")
	private List<Card> cards;

	public CardGroup() {
	}

	public String getCardGroup() {
		return this.cardGroup;
	}

	public void setCardGroup(String cardGroup) {
		this.cardGroup = cardGroup;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderSeq() {
		return this.orderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Card addCard(Card card) {
		getCards().add(card);
		card.setCardGroup(this);

		return card;
	}

	public Card removeCard(Card card) {
		getCards().remove(card);
		card.setCardGroup(null);

		return card;
	}
}