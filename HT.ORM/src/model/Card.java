package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TSN_CARD database table.
 * 
 */
@Entity
@Table(name="TSN_CARD")
@NamedQuery(name="Card.findAll", query="SELECT c FROM Card c")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idx;

	private String color;

	private String href;

	private String icon;

	@Lob
	private byte[] img;

	private String name;

	//bi-directional many-to-one association to LinkCardGroup
	@OneToMany(mappedBy="tsnCard")
	private List<LinkCardGroup> mapCardGroups;

	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="MAP_CARD_GROUP"
		, joinColumns={
			@JoinColumn(name="card_idx")
			}
		, inverseJoinColumns={
			@JoinColumn(name="group_idx")
			}
		)
	private List<Group> mstGroups;

	//bi-directional many-to-one association to PageTab
	@ManyToOne
	@JoinColumn(name="page_tab")
	private PageTab mstPageTab;

	public Card() {
	}

	public int getIdx() {
		return this.idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public byte[] getImg() {
		return this.img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LinkCardGroup> getMapCardGroups() {
		return this.mapCardGroups;
	}

	public void setMapCardGroups(List<LinkCardGroup> mapCardGroups) {
		this.mapCardGroups = mapCardGroups;
	}

	public LinkCardGroup addMapCardGroup(LinkCardGroup mapCardGroup) {
		getMapCardGroups().add(mapCardGroup);
		mapCardGroup.setTsnCard(this);

		return mapCardGroup;
	}

	public LinkCardGroup removeMapCardGroup(LinkCardGroup mapCardGroup) {
		getMapCardGroups().remove(mapCardGroup);
		mapCardGroup.setTsnCard(null);

		return mapCardGroup;
	}

	public List<Group> getMstGroups() {
		return this.mstGroups;
	}

	public void setMstGroups(List<Group> mstGroups) {
		this.mstGroups = mstGroups;
	}

	public PageTab getMstPageTab() {
		return this.mstPageTab;
	}

	public void setMstPageTab(PageTab mstPageTab) {
		this.mstPageTab = mstPageTab;
	}

}