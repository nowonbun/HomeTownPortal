package entity.bean;

import common.JsonConverter;

public class ObjectBean {
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String toJson() {
		return JsonConverter.create(this);
	}
}
