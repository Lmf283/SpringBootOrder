package com.lmf.order.wechatentity;

public abstract class AbstractBtn {
	
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AbstractBtn(String name) {
		super();
		this.name = name;
	}
}
