package com.hzu.homework.sysback.course.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hzu.homework.sysback.course.service.CourseService;
import com.hzu.homework.sysback.course.vo.CourseModel;
import com.hzu.homework.sysback.school.service.SchoolService;
import com.hzu.homework.sysback.school.vo.SchoolModel;
import com.hzu.homework.sysback.stucourserel.service.StudentCourseRelService;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;

@Controller("CourseController")
@RequestMapping("/sysback/course")
public class CourseController {
	private CourseService myService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private UserService userService;
	@Autowired 
	private StudentCourseRelService stuRelService;
	@Autowired
	public void setMyService(CourseService bs) {
		this.myService=bs;
	}
	
	@RequestMapping("/courseList")
	public String studentList(Model model){	
		return "sysback/course/courseList";
	}
	
	/**
	 * ©нЁлап╠М
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/getCourseList" }, method = {RequestMethod.GET}, produces = { "text/html;charset=UTF-8" })
	public String getCourseList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<CourseModel> list = myService.getAll();
		for(CourseModel item:list) {
			UserModel teacher = userService.getByUuid(item.getTeacherUuid());
			item.setTeacherName(teacher.getLoginName());
			SchoolModel school = schoolService.getByUuid(teacher.getSchoolUuid());
			item.setSchoolName(school.getSchoolName());
			int num=stuRelService.getStudentCountByCourseUuid(item.getUuid());
			item.setPersonNum(num+"");
		}
		return JSONArray.toJSONString(list);
	}

}
