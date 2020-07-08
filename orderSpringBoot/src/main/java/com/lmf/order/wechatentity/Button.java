package com.lmf.order.wechatentity;

import java.util.ArrayList;
import java.util.List;



public class Button {

	private List<AbstractBtn> button= new ArrayList<AbstractBtn>();
	
	public List<AbstractBtn> getButton() {
		return button;
	}
	public void setButton(List<AbstractBtn> button) {
		this.button = button;
	}


}
