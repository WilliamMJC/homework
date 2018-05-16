package com.hzu.homework.sysback.sysaccount.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hzu.homework.sysback.sysaccount.service.SysAccountService;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;


@Controller("LoginController2")
@RequestMapping("/sysback2")
public class LoginController {
	
	@Autowired
	private SysAccountService sysAccountService;
	@Autowired
	private UserService userService;
	
	
	/**
	 * 跳转到首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model){
		return "sysback/index";
	}
	
	/**
	 * 跳转到登录页
	 * @param model
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin(Model model){
		UserModel m =userService.getByUuid("rmMWmWXNkv3ifKy");
		System.out.println(m.toString());
		return "sysback/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("userName") String userName,@RequestParam("password") String password,
			HttpServletRequest request,HttpServletResponse response) {
		HttpSession session =request.getSession();
		session.setAttribute("loginName", userName);	
		return "sysback/index";
		
	}
	

}
