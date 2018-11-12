package com.hzu.homework.util;

import org.apache.shiro.SecurityUtils;

public class LoginUserHelper {
	public static final String LOGIN_USER = "Login_User";
	
	 public static String getLoginUserName(){		 
	    try{
	    	return (String)SecurityUtils.getSubject().getSession().getAttribute("loginName");   	
	     } 
	    catch (Exception err){
	    	
	    }
	    return "";
	  }
}
