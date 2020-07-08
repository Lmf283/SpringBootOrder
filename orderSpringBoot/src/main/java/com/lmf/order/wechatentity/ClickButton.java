package com.lmf.order.wechatentity;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data


public class ClickButton extends AbstractBtn{

	private String type="click";
	private String key;
	public ClickButton(String name,String key) {
		super(name);
		this.key = key;
	}
	
	
	
}
