package com.hzu.homework.app.course;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.Store;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzu.homework.emailreceive.EmailReceiveService;
import com.hzu.homework.sysback.course.service.CourseService;
import com.hzu.homework.sysback.course.vo.CourseModel;
import com.hzu.homework.sysback.homework.service.HomeworkService;
import com.hzu.homework.sysback.stucourserel.service.StudentCourseRelService;
import com.hzu.homework.sysback.teacourserel.service.TeacherCourseRelService;
import com.hzu.homework.sysback.teacourserel.vo.TeacherCourseRelModel;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;
import com.hzu.homework.util.ConnUtil;
import com.hzu.homework.util.JSONUtil;
import com.hzu.homework.util.StringUtil;
import com.hzu.homework.util.TokenUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("CourseApiController")
@RequestMapping("/api/course")
public class CourseApiController {
	private CourseService myService;
	@Autowired
	private UserService userService;
	@Autowired
	private EmailReceiveService receiveService;
	@Autowired
	private TeacherCourseRelService teacherRelService;
	@Autowired
	private StudentCourseRelService studentRelService;
	@Autowired
	private HomeworkService homeworkService;
	@Autowired
	public void setMyService(CourseService bs) {
		this.myService=bs;
	}
	
	
	/**
	 * 发布课程
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = { "/addCourse" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String addCourse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = userService.getByUuid(uuid);
		if(user.getUserType().equals("2")) {
			if(!StringUtil.isEmpty(user.getEmail())&&!StringUtil.isEmpty(user.getEmailPwd())) {
				Store store = ConnUtil.login("pop.qq.com", user.getEmail(), user.getEmailPwd());
				if(store !=null) {
					Message[] messages =receiveService.getAllMessage(store);
					CourseModel course =receiveService.getCourseModel(messages,uuid);
					//course.getUuid(),创建教师课程关联表
					if(course==null) {
						array.put("code", "false");
						array.put("msg", "邮件格式不对");
						System.out.println("----邮箱格式不对-----------");
					}else {
						TeacherCourseRelModel teaCourel = new TeacherCourseRelModel();
						teaCourel.setTeacherUuid(uuid);
						teaCourel.setCourseUuid(course.getUuid());
						teacherRelService.create(teaCourel);
						array.put("code", "success");
						array.put("msg", "邮箱登录成功");
						System.out.println("----登录成功-----------");				
					}
										
				}else {
					array.put("code", "false");
					array.put("msg", "邮箱登录失败，请检查密码或邮箱是否正确");
					System.out.println("----登录失败-----------");
				}
				
			}else {
				array.put("code", "false");
				array.put("msg", "请完善作业邮箱和邮箱授权码");
				System.out.println("----请完善作业邮箱和邮箱授权码-----------");
			}
			
		}else {
			array.put("code", "false");
			array.put("msg", "你没有权限操作");
			System.out.println("----wuxuanxian-----------");
		}
		return JSONUtil.parseJSON(array);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/getMyCourse" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getMyCourse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();
		String token = request.getParameter("token");	
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = userService.getByUuid(uuid);
		if(user.getUserType().equals("2")) {
			List<CourseModel> courseList =myService.getByTeacherUuid(uuid);
			for(CourseModel item:courseList) {
				JSONObject object = new JSONObject();
				object.put("course", item.getCourseName());
				object.put("uuid", item.getUuid());
				int countNum=studentRelService.getStudentCountByCourseUuid(item.getUuid());
				object.put("students", countNum+"");
				int times=homeworkService.getCountByCourseUuid(item.getUuid());
				object.put("works", times+"");
				arrays.add(object);
			}
			array.put("code", "success");
			array.put("course", arrays.toString());
		}else if(user.getUserType().equals("1")){
			List<String> courseUuids = studentRelService.getCourseByNo(uuid);
			for(int i=0;i<courseUuids.size();i++) {
				JSONObject object = new JSONObject();
				String courseUuid = courseUuids.get(i);
				object.put("uuid",courseUuid);
				CourseModel course = myService.getByUuid(courseUuid);
				object.put("course", course.getCourseName());
				arrays.add(object);
			}
			array.put("code", "success");
			array.put("course", arrays.toString());
		}
		return JSONUtil.parseJSON(array);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/getAllCourse" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getAllCourse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();
		String token = request.getParameter("token");			
		//List<String> courseNames = new ArrayList<String>();
		List<CourseModel> list=myService.getAll();
		for(CourseModel item:list) {
			JSONObject object = new JSONObject();
			object.put("course", item.getCourseName());
			arrays.add(object);
			//courseNames.add();
		}
		array.put("code", "success");
		array.put("courses", arrays.toString());
		return JSONUtil.parseJSON(array);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/getCourseStudent" }, method = {RequestMethod.POST}, produces = { "text/html;charset=UTF-8" })
	public String getCourseStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject array = new JSONObject();
		JSONArray arrays = new JSONArray();
		String token = request.getParameter("token");
		String courseUuid = request.getParameter("courseUuid");
		String uuid = TokenUtil.verifyToken(token).get("userUuid").asString();
		UserModel user = userService.getByUuid(uuid);
		if(user.getUserType().equals("2")) {
			List<String> no=studentRelService.getStudentNoByCourseUuid(courseUuid);
			if(no!=null) {
				for(int i=0;i<no.size();i++) {
					JSONObject object = new JSONObject();
					object.put("number", no.get(i));
					if(!StringUtil.isEmpty(userService.getStudentByNo(no.get(i)))) {
						String studentName=userService.getStudentByNo(no.get(i));					
						object.put("studentName", studentName);
					}else {
						object.put("studentName", "未注册");
					}
								
					arrays.add(object);
				}
				array.put("code", "success");
				array.put("student", arrays.toString());			
			}else {
				array.put("code", "false");
			}
			
		}else if(user.getUserType().equals("1")){
			
		}
		return JSONUtil.parseJSON(array);
	}
	
}
