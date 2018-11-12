package com.hzu.homework.sysback.user.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hzu.homework.sysback.sysaccount.service.SysAccountService;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;
import com.hzu.homework.util.JSONUtil;

@Controller("UserController")
@RequestMapping("/sysback/user")
public class UserController {
	@Autowired
	private SysAccountService sysAccountService;
	private UserService myService;
	@Autowired
	public void setMyService(UserService bs) {
		this.myService=bs;
	}
	
	@RequestMapping("/index")
	public String toList(Model model){
		
		return "sysback/index";
	}
	
	@RequestMapping("/main")
	public String main(Model model){	
		return "sysback/main";
	}
	
	@RequestMapping("/news")
	public String news(Model model){	
		return "sysback/news/newsList";
	}
	
	@RequestMapping("/teacherList")
	public String teacherList(Model model){	
		return "sysback/teacher/teacherList";
	}
	
	@RequestMapping("/teacherUpdate")
	public String teacherUpdateList(Model model){	
		return "sysback/teacher/teacherUpdate";
	}
	
	@RequestMapping("/toAddTeacher")
	public String toAddTeacher(Model model){	
		return "sysback/teacher/teacherAdd";
	}
	
	@RequestMapping("/studentList")
	public String studentList(Model model){	
		return "sysback/student/studentList";
	}
	
	@ResponseBody
	@RequestMapping(value = { "/login" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String login(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("进来了");
		String loginName = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(loginName+password);
		HttpSession session =request.getSession();
		boolean is=sysAccountService.login(loginName, password);
		if(is) {
			session.setAttribute("loginName", loginName);	
			return "success";
		}else {
			return "false";
		}
		//return "success";
	}
	
	/**
	 * 教师列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/getTeacherList" }, method = {RequestMethod.GET}, produces = { "text/html;charset=UTF-8" })
	public String getTeacherList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<UserModel> list = myService.getListByType("2");	
		return JSONArray.toJSONString(list);
	}
	
	/**
	 * 学生列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/getStudentList" }, method = {RequestMethod.GET}, produces = { "text/html;charset=UTF-8" })
	public String getStudentList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<UserModel> list = myService.getListByType("1");
		return JSONArray.toJSONString(list);
	}
	
	
	/*@RequestMapping("/addTeacher")
	public String addTeacher(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<UserModel> list = myService.getListByType("2");	
		return JSONArray.toJSONString(list);
	}*/

	
	@RequestMapping(value={"/addTeacher"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	public String addTeacher(Model model,@ModelAttribute("m")UserModel m,HttpServletRequest request){
		String loginName=m.getLoginName();
		String password = m.getLoginPwd();
		boolean is=myService.checkLogin(loginName, password);
		System.out.println(m.getLoginName());
		if(is) {
			return "success";
		}else {
			return "false";
		}
	}
	

}
