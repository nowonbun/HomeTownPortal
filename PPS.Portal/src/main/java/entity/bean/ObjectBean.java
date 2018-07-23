package entity.bean;

import common.JsonConverter;

public class ObjectBean {
	private Object data;
	private Object data2;
	private Object data3;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData2() {
		return data2;
	}

	public void setData2(Object data2) {
		this.data2 = data2;
	}

	public Object getData3() {
		return data3;
	}

	public void setData3(Object data3) {
		this.data3 = data3;
	}

	public String toJson() {
		return JsonConverter.create(this);
	}
}
