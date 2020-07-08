package com.lmf.order.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lmf.order.entity.User;
import com.lmf.order.mapper.UserMapper;
import com.lmf.order.util.AccessTokenUtil;
import com.lmf.order.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
@Slf4j
public class UserQueue {

	public static final BlockingQueue<String> QUEUE = new LinkedBlockingDeque<>();
	private static final int THREAD_COUNT = 10;
	
	 public static String userInfoUrl;
	 
	 public static UserMapper userDao;
	 //监听
	 public static void listen () {
	        for (int i = 0; i < THREAD_COUNT; i++) {
	            Thread thread = new Thread(new Runnable() {
	                @Override
	                public void run() {
	                    try {
	                        while (true) {
	                            //从队列拉取数据
	                            String openId = QUEUE.take();
	                            //access_token
	                            String accessToken = AccessTokenUtil.getToken();
	                            //声明客户端
	                            OkHttpClient client = new OkHttpClient();
	                            //构建Request
	                            Request request = new Request.Builder().url(userInfoUrl + "?access_token=" + accessToken + "&openid=" + openId +
	                                                 "&lang=zh_CN").build();
	                            Response response = client.newCall(request).execute();
	                            //如果请求成功，解析数据
	                            if (response.isSuccessful()) {
	                                String body = response.body().string();
	                                //得到一个JSON对象
	                                JSONObject object = JSON.parseObject(body);
	                                //声明系统用户
	                                User user = new User();
	                                user.setUserName(object.getString("nickname"));
	                                user.setImg(object.getString("headimgurl"));
	                                user.setUserId(object.getString("openid"));
	                                //信息入库
	                                userDao.regist(user);
	                            }
	                        }
	                    }catch (Exception e) {
	                        log.error("异步处理业务时失败..." + e);
	                    }
	                }
	            });
	            thread.start();
	        }
	        System.out.println("开始监听。。。");
	    }
}
