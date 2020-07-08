package com.lmf.order.wechatentity;
public class ViewButton extends AbstractBtn{

	
	private String type="view";
	private String url;
	public ViewButton(String name, String url) {
		super(name);
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
