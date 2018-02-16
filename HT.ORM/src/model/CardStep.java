package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the MST_CARD_STEP database table.
 * 
 */
@Entity
@Table(name = "MST_CARD_STEP")
@NamedQuery(name = "CardStep.findAll", query = "SELECT c FROM CardStep c")
public class CardStep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STEP")
	private String step;

	@Column(name = "NAME")
	private String name;

	// bi-directional many-to-one association to Card
	@OneToMany(mappedBy = "cardStep")
	private List<Card> cards;

	public CardStep() {
	}

	public String getStep() {
		return this.step;
	}

	public void setStep(String step) {
		this.step = step;
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

	public Card addMstCard(Card mstCard) {
		getCards().add(mstCard);
		mstCard.setCardStep(this);

		return mstCard;
	}

	public Card removeMstCard(Card mstCard) {
		getCards().remove(mstCard);
		mstCard.setCardStep(null);

		return mstCard;
	}

}