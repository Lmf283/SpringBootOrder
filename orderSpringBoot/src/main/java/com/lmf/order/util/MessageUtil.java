package com.lmf.order.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lmf.order.queue.UserQueue;
import com.lmf.order.wechatentity.Article;
import com.lmf.order.wechatentity.NewsMessage;
import com.lmf.order.wechatentity.TextMessage;
import com.thoughtworks.xstream.XStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  <p>
 *      格式化XML消息
 *  </p>
 *
 * 
 * */
public class MessageUtil {
	
	public static String projectUrl;
	
	public static String textXml(Map<String,String> map) throws InterruptedException, IOException{
		String xml ="";
		switch(map.get("MsgType")){
		case "event":
			xml = handleText(map);
			break;
		case "text":
			xml = handleQues(map);
		}
		return xml;
		
	}
	private static String handleQues(Map<String, String> map) throws IOException {
		XStream xstream = new XStream();
		
		String xml="";
		String content="";
		int number =0;
		int price=0;
		String userQuest =map.get("Content");
		if (userQuest.equals("1")) {
            content = "了解菜单，请点击预览菜单";
        }else if (userQuest.equals("2")) {
        	content = "在智能推荐中选择推荐类型";
        }else if (userQuest.equals("3")) {
        	content = "请进入个人中心-我的订单/个性化分析";
        }else if(userQuest.equals("4")){
        		number=2;
        		price=100;
        		return handleNews3(map,number,price);
        }else if(userQuest.equals("5")){
        		number=2;
        		price=200;
        		return handleNews3(map,number,price);
        }else if(userQuest.equals("6")){
        		number=5;
        		price=200;
				return handleNews3(map,number,price);
        }else if(userQuest.equals("7")){
    		number=5;
    		price=300;
        		return handleNews3(map,number,price);
        }else {
        	content = "请回复您要解决的问题编号：\n" +
                    "1、在哪里可以预览菜单\n" +
                    "2、免预览菜单，直接推荐\n" +
                    "3、如何查看个人订单信息和个性化分析";
        }
		TextMessage textMsg = new TextMessage(map,content);
		xstream.alias("xml",TextMessage.class);
		xml = xstream.toXML(textMsg);
		return xml;
	}
	private static String handleNews3(Map<String, String> map, int number, int price) throws IOException {
		XStream xstream = new XStream();
		String xml= "";
		String openid = map.get("FromUserName");
		 OkHttpClient client = new OkHttpClient();
		 Request request = new Request.Builder().url(projectUrl + "/buyer/findCommander?openid=" + openid  +
                "&number="+number+"&price="+price+"&lang=zh_CN").build();
		 Response response = client.newCall(request).execute();
		 String body = response.body().string();
		
		 JSONObject object = JSON.parseObject(body);
		 if(object != null){
			 String id = object.getString("productId");
			 String title = object.getString("productName");
			 String descript = "价格为：￥"+object.getString("productPrice")+"原材料："+object.getString("productDescription");
			 String picUrl = object.getString("productIcon");
			 String url = projectUrl+"/product/detail?openid="+openid+"&id="+id;
			 Article article = new Article(title,descript,picUrl,url);
			List<Article> Articles = new ArrayList<Article>();
			Articles.add(article);
			NewsMessage newsMsg = new NewsMessage(map,1,Articles);
			xstream.alias("xml",NewsMessage.class);
			xstream.alias("item",Article.class);
			xml = xstream.toXML(newsMsg);
		 }else{
			 TextMessage textMsg = new TextMessage(map,"暂无合适的推荐");
				xstream.alias("xml",TextMessage.class);
				xml = xstream.toXML(textMsg);
		 }
		return xml;
		
	}
	public static String handleText(Map<String,String> map) throws InterruptedException, IOException{
		XStream xstream = new XStream();
		String content = "";
		String xml= "";
		String msgType = map.get("MsgType");
		if(msgType.equals("event")){
			 String event = map.get("Event");
			 String fromUser = map.get("FromUserName");
			if (event.equals("subscribe")) {
                content = "欢迎您关注点餐平台！";
                //放入阻塞队列
                UserQueue.QUEUE.put(fromUser);
                TextMessage textMsg = new TextMessage(map,content);
        		xstream.alias("xml",TextMessage.class);
        		xml = xstream.toXML(textMsg);
            }else if (event.equals("CLICK")) {
                //如果是点击事件，获取菜单的key值，实现我们自己的业务逻辑
                String key = map.get("EventKey");
	                if (key.equals("one-commend")) {
	                	xml = handleNews(map); 
	                }
	                if (key.equals("many-commend")) {
	                    xml = handleNews2(map);
	                }
		}/*else if(msgType.equals("text")){
			String userQuest =map.get("Content");
			if (userQuest.equals("1")) {
                content = "了解菜单，请点击预览菜单";
            }else if (userQuest.equals("2")) {
            	content = "在智能推荐中选择推荐类型";
            }else if (userQuest.equals("3")) {
            	content = "如想参加商家活动请进入个人中心-商家活动";
            }else {
            	content = "请回复您要解决的问题编号：\n" +
                        "1、在哪里可以预览菜单\n" +
                        "2、免预览菜单，直接推荐\n" +
                        "3、在哪里可以领取优惠券";
            }
			TextMessage textMsg = new TextMessage(map,content);
    		xstream.alias("xml",TextMessage.class);
    		xml = xstream.toXML(textMsg);
		}*/
		
			
	}
		return xml;
}
	
	
	//回复图文
	public static String handleNews(Map<String,String> map) throws IOException{
		String openid = map.get("FromUserName");
		 OkHttpClient client = new OkHttpClient();
		 Request request = new Request.Builder().url(projectUrl + "/buyer/findBest?openid=" + openid  +
                 "&lang=zh_CN").build();
		 Response response = client.newCall(request).execute();
		 String body = response.body().string();
		 JSONObject object = JSON.parseObject(body);
		 String id = object.getString("productId");
		 String title = object.getString("productName");
		 String descript = "价格为：￥"+object.getString("productPrice")+"原材料："+object.getString("productDescription");
		 String picUrl = object.getString("productIcon");
		 String url = projectUrl+"/product/detail?openid="+openid+"&id="+id;
		 Article article = new Article(title,descript,picUrl,url);
		List<Article> Articles = new ArrayList<Article>();
		Articles.add(article);
		NewsMessage newsMsg = new NewsMessage(map,1,Articles);
		XStream xstream = new XStream();
		String xml= "";
		xstream.alias("xml",NewsMessage.class);
		xstream.alias("item",Article.class);
		xml = xstream.toXML(newsMsg);
		return xml;
	}
   //套餐推荐图文处理
	public static String handleNews2(Map<String,String> map) throws IOException{
		String content="请输入可以接受的套餐总金额数和用餐人数编号：\n"
				+ "4. 小于100元的双人套餐\n"
				+ "5. 大于100小于150的双人套餐\n"
				+ "6. 小于200的3到5人套餐\n"
				+ "7. 大于200小于300的3到5人套餐\n";
				
		String xml="";
		XStream xstream = new XStream();
		TextMessage textMsg = new TextMessage(map,content);
		xstream.alias("xml",TextMessage.class);
		xml = xstream.toXML(textMsg);
		return xml;
		
	}
}
