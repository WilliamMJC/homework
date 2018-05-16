package com.hzu.homework.app.user;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzu.homework.sysback.course.vo.CourseModel;
import com.hzu.homework.sysback.school.service.SchoolService;
import com.hzu.homework.sysback.school.vo.SchoolModel;
import com.hzu.homework.sysback.stucourserel.service.StudentCourseRelService;
import com.hzu.homework.sysback.teacourserel.service.TeacherCourseRelService;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;
import com.hzu.homework.util.JSONUtil;
import com.hzu.homework.util.MD5Util;
import com.hzu.homework.util.MailSendUtil;
import com.hzu.homework.util.RandomUtil;
import com.hzu.homework.util.StringUtil;
import com.hzu.homework.util.TokenUtil;
import com.hzu.homework.util.UserLoginUuidHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("UserApiController")
@RequestMapping("/api/user")
public class UserApiController {
	
	private UserService myService;
	@Autowired
	public void setMyService(UserService bs) {
		this.myService=bs;
	}
	
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private StudentCourseRelService stuCouRelService;
	@Autowired
	private TeacherCourseRelService teaCouRelService;
	
	/**
	 * 注册功能
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/register" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String register(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject array = new JSONObject();
		String code=request.getParameter("mail_code");
		String userName = request.getParameter("user");
		String password = request.getParameter("password");
		String privEmail = request.getParameter("mail_name");
		//比对验证码
		if(!checkUserName(userName)) {
			if(checkVerifyCode(request,code)) {
				System.out.println("-------------验证码验证成功----------------");
				UserModel user = new UserModel();
				user.setLoginName(userName);
				String md5Pwd = MD5Util.md5(password);
				user.setLoginPwd(md5Pwd);
				user.setPrivEmail(privEmail);
				myService.create(user);
				String Path = request.getSession().getServletContext().getRealPath(userName);
				System.out.println(Path);
				File file=new File(Path);
				if(!file.exists()){
					file.mkdirs();
				}
				array.put("code", "success");
				array.put("msg", "注册成功");		
			}else{
				array.put("code", "false");
				array.put("msg", "验证码错误");
			}
		}else {
			System.out.println("-------------验证码验证失败----------------");
			array.put("code", "same_name");
			array.put("msg", "用户名已存在");
		}
		
		return JSONUtil.parseJSON(array);
	}
	
	/**
	 * 登录功能
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/login" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String login(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject array = new JSONObject();
		String token="";
		String loginName = request.getParameter("user");
		String password = request.getParameter("password");
		if(checkUserName(loginName)) {
			
			boolean result=myService.checkLogin(loginName,password);
			if(result) {				
				UserModel user = myService.getLoginModel(loginName, password);
				if(user.getUserType().equals("2")) {
					 token =TokenUtil.getToken(true, user.getLoginName(), user.getUuid());
				}else {
					token =TokenUtil.getToken(false, user.getLoginName(), user.getUuid());
				}
				System.out.println("token:"+token);
				array.put("token", token);
				array.put("code", "success");
				array.put("msg", "登录成功");
			}else {
				array.put("code", "false");
				array.put("msg", "用户名或密码错误");
			}
		}else{
			array.put("code", "false");
			array.put("msg", "用户名或密码错误");
		}
		return JSONUtil.parseJSON(array);
	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/getUserInfo" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = myService.getByUuid(uuid);
		JSONObject array = new JSONObject();
		array.put("userName", user.getLoginName());
		if(!StringUtil.isEmpty(user.getSchoolUuid())) {
			SchoolModel school = schoolService.getByUuid(user.getSchoolUuid());
			array.put("schoolName", school.getSchoolName());
		}else {
			array.put("schoolName","");
		}
		//用户为学生时
		if(user.getUserType().equals("1")) {
			array.put("userType", "1");
			if(!StringUtil.isEmpty(user.getStudentNo())) {
				array.put("studentNo", user.getStudentNo());
			}else {
				array.put("studentNo","");
			}
			
		}else if(user.getUserType().equals("2")) {
			array.put("userType", "2");
			if(!StringUtil.isEmpty(user.getEmail())) {
				array.put("emailName",user.getEmail());
			}else {
				array.put("emailName","");
			}
			if(!StringUtil.isEmpty(user.getEmailPwd())) {
				array.put("emailPwd",user.getEmailPwd());
			}else {
				array.put("emailPwd","");
			}
			
			//array.put("emailName",user.getEmail());
			//array.put("emailPwd",user.getEmailPwd());
		}else {
			array.put("emailName","");
			array.put("emailPwd","");
			array.put("studentNo","");
		}		
		return JSONUtil.parseJSON(array);
		
	}
	
	
	/**
	 * 更新用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateUserInfo" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String updateUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return "";
		
	}
	
	/**
	 * 更新用户名信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateUserName" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String updateUserName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = myService.getByUuid(uuid);
		String userName = request.getParameter("userName");
		JSONObject array = new JSONObject();
		if(!checkUserName(userName) ||user.getLoginName().equals(userName)) {
			user.setLoginName(userName);
			myService.update(user);
			array.put("code", "success");
		}else{
			array.put("code", "false");		
		}
		return JSONUtil.parseJSON(array);	
	}
	
	/**
	 * 更新学校信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateSchoolName" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String updateSchoolName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = myService.getByUuid(uuid);
		String schoolName = request.getParameter("schoolName");
		if(!schoolService.checkSchoolName(schoolName)) {
			SchoolModel school = new SchoolModel();
			school.setSchoolName(schoolName);
			schoolService.create(school);		
		}
		String schoolUuid = schoolService.getUuidByName(schoolName);
		user.setSchoolUuid(schoolUuid);
		myService.update(user);				
		JSONObject array = new JSONObject();
		array.put("code", "success");
		return JSONUtil.parseJSON(array);
		
	}
	
	/**
	 * 更新学号信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateStudentNo" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String updateStudentNo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = myService.getByUuid(uuid);
		String studentNo = request.getParameter("studentNo");
		JSONObject array = new JSONObject();
		if(stuCouRelService.getCourseByNo(studentNo)!=null) {
			user.setStudentNo(studentNo);
			myService.update(user);
			array.put("code", "success");
		}else {
			array.put("code", "false");
		}
		return JSONUtil.parseJSON(array);
		
	}
	
	/**
	 * 更新邮箱信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateEmailName" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String updateEmailName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = myService.getByUuid(uuid);
		String emailName = request.getParameter("emailName");
		JSONObject array = new JSONObject();
		user.setEmail(emailName);
		myService.update(user);
		array.put("code", "success");	
		return JSONUtil.parseJSON(array);
		
	}
	
	/**
	 * 更新邮箱密码信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateEmailPwd" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String updateEmailPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = myService.getByUuid(uuid);
		String emailPwd = request.getParameter("emailPwd");
		JSONObject array = new JSONObject();
		user.setEmailPwd(emailPwd);
		myService.update(user);
		array.put("code", "success");	
		return JSONUtil.parseJSON(array);	
	}
	
	
	public boolean checkEmail(String Email) {
		
		return false;
	}
	
	public String checkUserType(String uuid) {
		
		return "";
	}
	
	public boolean checkUserName(String userName) {
		return myService.checkUserName(userName);
	}
	
	/**
	 * 验证验证码
	 * @param request
	 * @param code
	 * @return
	 */
	public boolean checkVerifyCode(HttpServletRequest request,String code) {
		HttpSession session = request.getSession();
		String code2 =(String)session.getAttribute("code");
		System.out.println("session'验证码为："+code2);
		System.out.println("传过来'验证码为："+code);
		if(code.equals(code2)) {
			return true;
		}
		//默认为true
		return true;
	}
	
	public boolean checkStudentNo(String no) {
		return false;
	}
	
	
	
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = { "/getVerifyCode" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String verifyCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONObject array = new JSONObject();
		String mailName=request.getParameter("mail_name");
		//查询邮箱账号是否已注册
		if(!checkEmail(mailName)) {
			//生成验证码
			String vcode = RandomUtil.generateString(5);
			HttpSession session = request.getSession();
			session.setAttribute("code", vcode);
			MailSendUtil mailsend =new MailSendUtil("smtp.qq.com","1050416617@qq.com","jwovgwaypwrebecd");
			try {
				mailsend.send("smtp.qq.com",mailName,"收发作业验证码","你的验证码为:"+vcode);
			} catch (Exception e) {					
				e.printStackTrace();
				array.put("code", "error");
				array.put("msg", "验证码发生错误");
			}			
			array.put("code", "success");
			array.put("msg", "获取成功");
		}else {
			array.put("code", "false");
			array.put("msg", "该邮箱已注册");		
		}
		return JSONUtil.parseJSON(array);	
	}
	
	/**
	 * 设置身份功能
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/setIdentity" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String setIdentity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		String type = request.getParameter("type");
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = myService.getByUuid(uuid);
		user.setUserType(type);
		myService.update(user);
		array.put("code", "success");
		array.put("msg", "设置成功");
		return JSONUtil.parseJSON(array);	
	}
	
	/**
	 * 获取身份功能
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/getIdentity" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getIdentity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = myService.getByUuid(uuid);
		array.put("code", user.getUserType());
		array.put("msg", "设置成功");
		return JSONUtil.parseJSON(array);	
	}
	
	@ResponseBody
	@RequestMapping(value = { "/getMyTeacher" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getMyCourse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = myService.getByUuid(uuid);
		if(user.getUserType().equals("2")) {
			
		}else if(user.getUserType().equals("1")){
			List<String> courseUuids = stuCouRelService.getCourseByNo(user.getStudentNo());
			for(int i=0;i<courseUuids.size();i++) {
				JSONObject object = new JSONObject();
				String courseUuid = courseUuids.get(i);
				String teacherUuid=teaCouRelService.getTeacherUuidByCourseUuid(courseUuid);
				UserModel teacher= myService.getByUuid(teacherUuid);				
				object.put("teacher",teacher.getLoginName());
				arrays.add(object);
			}
			array.put("code", "success");
			array.put("teachers", arrays.toString());
		}
		return JSONUtil.parseJSON(array);
	}
}
