package com.lmf.order.wechatentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class NewsMessage extends Message {
	private int ArticleCount=1;
	private List<Article> Articles = new ArrayList<Article>();
	public NewsMessage(Map<String, String> map, int articleCount, List<Article> articles) {
		super(map);
		setMsgType("news");
		ArticleCount = articleCount;
		this.Articles = articles;
	}
	
}
