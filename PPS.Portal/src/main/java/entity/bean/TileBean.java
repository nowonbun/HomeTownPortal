package entity.bean;

public class TileBean extends ObjectBean {
	private String typeHeaderClass;
	private String background;
	private String header;
	private String border;
	private String body;
	private String href;
	private boolean menu;
	private String control;
	private String template;

	public String getTypeHeaderClass() {
		return typeHeaderClass;
	}

	public void setTypeHeaderClass(String typeHeaderClass) {
		this.typeHeaderClass = typeHeaderClass;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isMenu() {
		return menu;
	}

	public void setMenu(boolean menu) {
		this.menu = menu;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
