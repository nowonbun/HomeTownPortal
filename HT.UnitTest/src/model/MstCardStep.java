package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MST_CARD_STEP database table.
 * 
 */
@Entity
@Table(name="MST_CARD_STEP")
@NamedQuery(name="MstCardStep.findAll", query="SELECT m FROM MstCardStep m")
public class MstCardStep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="STEP")
	private String step;

	@Column(name="NAME")
	private String name;

	//bi-directional many-to-one association to MstCard
	@OneToMany(mappedBy="mstCardStep")
	private List<MstCard> mstCards;

	public MstCardStep() {
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

	public List<MstCard> getMstCards() {
		return this.mstCards;
	}

	public void setMstCards(List<MstCard> mstCards) {
		this.mstCards = mstCards;
	}

	public MstCard addMstCard(MstCard mstCard) {
		getMstCards().add(mstCard);
		mstCard.setMstCardStep(this);

		return mstCard;
	}

	public MstCard removeMstCard(MstCard mstCard) {
		getMstCards().remove(mstCard);
		mstCard.setMstCardStep(null);

		return mstCard;
	}

}