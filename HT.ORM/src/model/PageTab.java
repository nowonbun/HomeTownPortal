package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MST_PAGE_TAB database table.
 * 
 */
@Entity
@Table(name="MST_PAGE_TAB")
@NamedQuery(name="PageTab.findAll", query="SELECT p FROM PageTab p")
public class PageTab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idx;

	@Column(name="PAGE_TAB")
	private String pageTab;

	//bi-directional many-to-one association to Card
	@OneToMany(mappedBy="mstPageTab")
	private List<Card> tsnCards;

	public PageTab() {
	}

	public int getIdx() {
		return this.idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getPageTab() {
		return this.pageTab;
	}

	public void setPageTab(String pageTab) {
		this.pageTab = pageTab;
	}

	public List<Card> getTsnCards() {
		return this.tsnCards;
	}

	public void setTsnCards(List<Card> tsnCards) {
		this.tsnCards = tsnCards;
	}

	public Card addTsnCard(Card tsnCard) {
		getTsnCards().add(tsnCard);
		tsnCard.setMstPageTab(this);

		return tsnCard;
	}

	public Card removeTsnCard(Card tsnCard) {
		getTsnCards().remove(tsnCard);
		tsnCard.setMstPageTab(null);

		return tsnCard;
	}

}