package com.lmf.order.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lmf.order.queue.UserQueue;
import com.lmf.order.util.AccessTokenUtil;
import com.lmf.order.util.CookieUtil;
import com.lmf.order.util.MessageUtil;
import com.lmf.order.util.XMLUtil;
import com.lmf.order.wechatentity.Button;
import com.lmf.order.wechatentity.ClickButton;
import com.lmf.order.wechatentity.SubButton;
import com.lmf.order.wechatentity.ViewButton;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
@Slf4j
public class WeChatController {

	@Value("${wechat.menuUrl}")
	
	private String menuUrl;
	
	@Value("${projectUrl}")
	private String projectUrl;
	
	@Value("${wechat.appId}")
	private String appId;
	
	@Value("${wechat.secret}")
	private String secret;
	
	
	
	/**
	 * 接入微信
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@GetMapping(value = "/wechat")
    public Object check (String signature , String timestamp ,
                         String nonce ,
                         String echostr) {
        return echostr;
    }
	
	 /**
	  * 与微信交互
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	@PostMapping(value = "/wechat" , produces = {"application/xml;charset=utf-8"})
	    public Object doRequest (HttpServletRequest request) throws IOException, InterruptedException {
	        //解析XML文档，判断消息类型，判断事件类型，实现沟通逻辑

	        //解析XML文档，转换为Map，可读性更高，获取数据方便
	        Map<String , String> map = XMLUtil.getMap(request.getInputStream());
	        System.out.println(map);
	        String reposexml = MessageUtil.textXml(map);
	        System.out.println(reposexml);
	        return MessageUtil.textXml(map);
	    }
	/**
     *  创建菜单
     * */
    @GetMapping(value = "/create-menu")
    public Object creatMenu () {
    	String url = URLEncoder.encode(projectUrl+"/buyer/list");
    	Button btn = new Button();
    	ViewButton viewbtn = new ViewButton("预览菜单","https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect ");
        btn.getButton().add(viewbtn);
    	
    	SubButton subButton = new SubButton("智能推荐");
    	subButton.getSub_button().add(new ClickButton("个人推荐","one-commend"));
    	
    	subButton.getSub_button().add(new ClickButton("团体推荐","many-commend"));
    	btn.getButton().add(subButton);
    	SubButton subButton1 = new SubButton("个人中心");
    	String orderUrl = URLEncoder.encode(projectUrl+"/buyer/findAllOrder");
    	String analysisUrl = URLEncoder.encode(projectUrl+"/buyer/analysis");
    	subButton1.getSub_button().add(new ViewButton("我的订单","https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+orderUrl+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect "));
    	subButton1.getSub_button().add(new ViewButton("个性化分析","https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+analysisUrl+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect "));
    	//subButton1.getSub_button().add(new ViewButton("商家活动",projectUrl+"/buyer/list"));
    	btn.getButton().add(subButton1);
    	String jsonObjet = JSONObject.toJSONString(btn);
        String token = AccessTokenUtil.getToken();

        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8") , jsonObjet);
            System.out.println(jsonObjet);
            Request request = new Request.Builder().url(menuUrl + "?access_token=" + token).post(requestBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("创建菜单成功");
            }
        }catch (Exception e) {
            log.error("创建菜单出错");
        }
        return "success";
    }
   
    
}
