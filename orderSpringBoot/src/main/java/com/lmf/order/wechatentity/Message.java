package com.lmf.order.wechatentity;

import java.util.Map;

import lombok.Data;

@Data
public class Message {
	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String MsgType;
	public Message(Map<String,String> map){
		this.ToUserName = map.get("FromUserName");
		this.FromUserName = map.get("ToUserName");
		this.CreateTime = System.currentTimeMillis()/1000+"";
	}
}
