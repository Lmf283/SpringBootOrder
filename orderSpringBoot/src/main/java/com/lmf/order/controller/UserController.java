package com.lmf.order.controller;


import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.lmf.order.entity.Result;
import com.lmf.order.entity.User;
import com.lmf.order.service.UserService;
import com.lmf.order.util.KeyUtil;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/login"})
    public String loginpage() {
        return "login";
    }
    @RequestMapping(value = {"/register"})
    public String registerpage() {
        return "register";
    }
    
    @RequestMapping(value = {"/index"})
    public String index() {
        return "index";
    }
    
    @RequestMapping(value = {"/userInfo"})
    public ModelAndView userInfo(HttpServletRequest request) {
    	String op = request.getParameter("op");
    	ModelAndView mav = new ModelAndView();
    	Cookie[] cookies =request.getCookies();
    	String userInfo="";
    	for(Cookie c : cookies){
    		if(c.getName().equals("userInfo")){
    			userInfo = c.getValue();
    			break;
    		}
    	}
    	mav.addObject("userInfo", userInfo);
    	if(op.equals("userInfo")){
    		mav.setViewName("userInfo");
    	} else if(op.equals("editPsw")){
    		mav.setViewName("editPsw");
    	}
    	
        return mav;
    }
    
    @PostMapping(value = {"/editPsw"})
    public ModelAndView editPsw(HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView();
    	String id =request.getParameter("ID");
    	String password = request.getParameter("newpwd");
    	int flag = userService.editPsw(id,password);
    	String msg="";
    	if(flag==1){
    		msg="修改成功";
    	}else{
    		msg="修改失败";
    	}
    	mav.addObject("msg", msg);
    	mav.setViewName("index");
        return mav;
    }
    /**
     * 注册
     * @param user 参数封装
     * @return Result
     * @throws IOException 
     */
    @PostMapping(value = "/regist")
    public ModelAndView regist(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	//request.setCharacterEncoding("UTF_8");
    	ModelAndView mav = new ModelAndView();
    	String name = request.getParameter("userName");
    	String password = request.getParameter("passWord");
    	String tel = request.getParameter("phone");
    	String id = KeyUtil.getUniqueKey();
    	String img = request.getParameter("img");
    	User user = new User(name,password,tel,id,img);
    	Result result=userService.regist(user);
    	mav.addObject("result", result);
    	if(result.isSuccess()){
    		mav.setViewName("index");
    		 return mav;
    	}else{
    		mav.setViewName("register");
    		return mav;
    	}
       
    }

    /**
     * 登录
     * @param user 参数封装
     * @return Result
     * @throws IOException 
     */
    @PostMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	ModelAndView mav = new ModelAndView();
    	User user = new User();
    	String name = request.getParameter("userName");
    	String password = request.getParameter("passWord");
    	user.setUserName(name);
    	user.setPassWord(password);
    	user.setUserId("login");
    	Result result = userService.login(user);
    	Object object = JSONObject.toJSON(result.getDetail());
    	String userStr = JSONObject.toJSONString(object);
    	Cookie userInfo= new Cookie("userInfo",userStr);
    	response.addCookie(userInfo);
    	mav.addObject("result", result);
    	 if(result.isSuccess()){
    		 mav.setViewName("index");
    		 return mav;
    	 }
    	 else{
    		mav.setViewName("login");
    		 return mav;
    	 }
    	
       
    }
}

