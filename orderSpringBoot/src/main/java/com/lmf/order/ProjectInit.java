package com.lmf.order;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.lmf.order.mapper.UserMapper;
import com.lmf.order.queue.UserQueue;
import com.lmf.order.util.AccessTokenUtil;
import com.lmf.order.util.MessageUtil;
import com.lmf.order.util.RedisUtil;

/**
 *  <p>
 *      初始化项目
 *  </p>
 *
 * */
@Component
public class ProjectInit implements ApplicationRunner {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    
    @Value("${wechat.accessTokenUrl}")
    private String accessTokenUrl;
    //应用ID
    @Value("${wechat.appId}")
    private String appId;
    //开发者密钥
    @Value("${wechat.secret}")
    private String secret;
    
	@Value("${projectUrl}")
	private  String projectUrl;

    //获取用户信息的接口地址（通过UID机制）
    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;
    
    @Autowired
    private UserMapper userDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Redis> redisList = new ArrayList<>();
        //127.0.0.1:6379
        redisList.add(new Redis(host + ":" + port , password));
        RedisUtil.init(redisList);
       System.out.println("初始化redis成功");

        //为工具类的相关字段，赋值
        AccessTokenUtil.accessTokenUrl =this.accessTokenUrl;
        AccessTokenUtil.appId = this.appId;
        AccessTokenUtil.secret = this.secret;

        MessageUtil.projectUrl = this.projectUrl;
        //绑定阻塞队列的变量值
        UserQueue.userInfoUrl = this.userInfoUrl;
        UserQueue.userDao = this.userDao;
        //唤醒后台任务
        UserQueue.listen();
    }
}
