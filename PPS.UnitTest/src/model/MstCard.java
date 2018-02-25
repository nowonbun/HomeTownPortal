package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MST_CARD database table.
 * 
 */
@Entity
@Table(name="MST_CARD")
@NamedQuery(name="MstCard.findAll", query="SELECT m FROM MstCard m")
public class MstCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODE")
	private String code;

	@Column(name="COLOR")
	private String color;

	@Column(name="HREF")
	private String href;

	@Column(name="ICON")
	private String icon;

	@Lob
	@Column(name="IMG")
	private byte[] img;

	@Column(name="NAME")
	private String name;

	//bi-directional many-to-many association to MstGroup
	@ManyToMany
	@JoinTable(
		name="MAP_CARD_GROUP"
		, joinColumns={
			@JoinColumn(name="CARD_CODE")
			}
		, inverseJoinColumns={
			@JoinColumn(name="GROUP_CODE")
			}
		)
	private List<MstGroup> mstGroups;

	//bi-directional many-to-one association to MstCardStep
	@ManyToOne
	@JoinColumn(name="STEP")
	private MstCardStep mstCardStep;

	public MstCard() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public List<MstGroup> getMstGroups() {
		return this.mstGroups;
	}

	public void setMstGroups(List<MstGroup> mstGroups) {
		this.mstGroups = mstGroups;
	}

	public MstCardStep getMstCardStep() {
		return this.mstCardStep;
	}

	public void setMstCardStep(MstCardStep mstCardStep) {
		this.mstCardStep = mstCardStep;
	}

}