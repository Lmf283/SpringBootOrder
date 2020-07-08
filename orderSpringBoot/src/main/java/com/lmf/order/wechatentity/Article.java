package com.lmf.order.wechatentity;

import lombok.Data;

@Data
public class Article {
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;
	public Article(String title, String description, String picUrl, String url) {
		super();
		Title = title;
		Description = description;
		PicUrl = picUrl;
		Url = url;
	}
	
}
