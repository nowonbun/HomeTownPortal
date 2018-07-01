package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MST_CARD_TYPE")
@NamedQuery(name = "CardType.findAll", query = "SELECT c FROM CardType c")
public class CardType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CARD_TYPE")
	private String cardType;

	@Column(name = "NAME")
	private String name;

	// bi-directional many-to-one association to Card
	@OneToMany(mappedBy = "cardType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Card> cards;

	public CardType() {
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

}