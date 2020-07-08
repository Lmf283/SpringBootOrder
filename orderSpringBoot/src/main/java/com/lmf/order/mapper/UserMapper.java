package com.lmf.order.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.lmf.order.entity.User;


@Mapper
@Repository
public interface UserMapper {
	/**
     * 查询用户名是否存在，若存在，不允许注册
     * 注解@Param(value) 若value与可变参数相同，注解可省略
     * 注解@Results  列名和字段名相同，注解可省略
     * @param username
     * @return
     */
    @Select(value = "select u.userName,u.passWord from t_user u where u.userName=#{username}")
    @Results
            ({@Result(property = "userName",column = "userName"),
              @Result(property = "passWord",column = "passWord")})
    User findUserByName(@Param("username") String username);

    /**
     * 注册  插入一条user记录
     * @param user
     * @return
     */
    @Insert("insert into t_user(userName, passWord, tel, userId,img) values(#{userName}, #{passWord}, #{tel}, #{userId},#{img})")
    //加入该注解可以保存对象后，查看对象插入id
    @Options(useGeneratedKeys = true,keyProperty = "userId",keyColumn = "userId")
    void regist(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    @Select("select * from t_user u where u.userName = #{userName} and passWord = #{passWord}")
    User login(User user);
    
  
   @Update("update t_user u set u.passWord =#{password} where u.userId =#{id}")
    int editPsw(@Param("id")String id,@Param("password")String password);
}
