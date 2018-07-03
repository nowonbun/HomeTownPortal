package entity.bean;

import java.util.List;

import entity.SelectNode;

public class CardBean extends ObjectBean {
	private String code;
	private String name;
	private String title;
	private String description;
	private String img;
	private boolean hasImg;
	private String icon;
	private String color;
	private String step;
	private String type;
	private int seq;
	private String controller;
	private String template;
	private List<SelectNode> cardtypelist;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public boolean isHasImg() {
		return hasImg;
	}

	public void setHasImg(boolean hasImg) {
		this.hasImg = hasImg;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getController() {
		return this.controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<SelectNode> getCardtypelist() {
		return cardtypelist;
	}

	public void setCardtypelist(List<SelectNode> cardtypelist) {
		this.cardtypelist = cardtypelist;
	}

}
