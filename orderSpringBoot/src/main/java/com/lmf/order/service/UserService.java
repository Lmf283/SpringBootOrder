package com.lmf.order.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmf.order.entity.Result;
import com.lmf.order.entity.User;
import com.lmf.order.mapper.UserMapper;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    public int editPsw(String id,String password){
    	int flag = userMapper.editPsw(id, password);
    	return flag;
    }
    
    /*public User findOne(String id){
    	User userInfo = userMapper.findByuserId(id);
    	return userInfo;
    }*/
    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    public Result regist(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User existUser = userMapper.findUserByName(user.getUserName());
            if(existUser != null){
                //如果用户名已存在
                result.setMsg("用户名已存在");
            }else{
                userMapper.regist(user);
                result.setMsg("注册成功");
                result.setSuccess(true);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 登录
     * @param user 用户名和密码
     * @return Result
     */
    public Result login(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User getUser= userMapper.login(user);
            if(getUser.getUserId() == null){
                result.setMsg("用户名或密码错误");
            }else{
                result.setMsg("登录成功");
                result.setSuccess(true);
                result.setDetail(getUser);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
