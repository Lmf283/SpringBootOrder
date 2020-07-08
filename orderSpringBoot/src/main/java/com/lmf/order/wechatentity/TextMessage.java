package com.lmf.order.wechatentity;

import java.util.Map;

import lombok.Data;
@Data
public class TextMessage extends Message{

	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String MsgType;
	private String Content;
	public TextMessage(Map<String, String> map,String content) {
		super(map);
		this.setMsgType("text");
		this.Content=content;
	}
	
}
