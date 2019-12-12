package com.lingkj.project.user.dto;

import lombok.Data;

@Data
public class UserLogonDto {
   private   String  mail;
   private  String  code;
   private   String  name;
   private  String  password;
   private  String confirmPassword;
}
