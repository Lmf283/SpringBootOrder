package com.lmf.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
public class User {
 private String userName;
 private String passWord;
 private String tel;
 private String userId;
 private String img;
}
