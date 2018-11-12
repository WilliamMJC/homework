package com.hzu.homework.sysback.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzu.homework.sysback.sysaccount.service.SysAccountService;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;

@Controller("AdminLoginController")
@RequestMapping("/sysback")
public class AdminLoginController {
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
		return "sysback/login";
	}
	
	@ResponseBody
	@RequestMapping(value = { "/login" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String login(@RequestParam("userName") String userName,@RequestParam("password") String password,
			HttpServletRequest request,HttpServletResponse response) {
		System.out.println("进来了");
		HttpSession session =request.getSession();
		boolean is=sysAccountService.login(userName, password);
		if(is) {
			session.setAttribute("loginName", userName);	
			return "success";
		}else {
			return "false";
		}	
	}

}
