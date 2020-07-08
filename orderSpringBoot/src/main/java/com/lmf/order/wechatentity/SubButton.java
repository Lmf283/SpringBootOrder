package com.lmf.order.wechatentity;

import java.util.ArrayList;
import java.util.List;

public class SubButton extends AbstractBtn{

	private List<AbstractBtn> sub_button = new ArrayList<>();

	public List<AbstractBtn> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<AbstractBtn> sub_button) {
		this.sub_button = sub_button;
	}

	public SubButton(String name) {
		super(name);
	}
	

	
	

}
