package entity;

import model.Card;
import reference.CardTypeMaster;

public class NavigateNode {
	private boolean menu;
	private String href;
	private String name;
	private String control;
	private String template;

	public NavigateNode(boolean menu, String href, String name, String control, String template) {
		this.menu = menu;
		this.href = href;
		this.name = name;
		this.control = control;
		this.template = template;
	}
	
	public NavigateNode(Card card) {
		menu = CardTypeMaster.equals(card.getCardType(), CardTypeMaster.getEventMenuCardType()) || 
			   CardTypeMaster.equals(card.getCardType(), CardTypeMaster.getImageMenuCardType());
		href = card.getHref();
		name = card.getName();
		control = card.getControl();
		template = card.getTemplate();
	}
	
	public boolean isMenu() {
		return menu;
	}

	public String getHref() {
		return href;
	}

	public String getName() {
		return name;
	}

	public String getControl() {
		return control;
	}

	public String getTemplate() {
		return template;
	}


}
