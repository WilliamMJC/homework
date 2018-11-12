package com.hzu.homework.sysback.homework.controller;

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
import com.hzu.homework.sysback.comment.service.CommentService;
import com.hzu.homework.sysback.course.service.CourseService;
import com.hzu.homework.sysback.course.vo.CourseModel;
import com.hzu.homework.sysback.homework.service.HomeworkService;
import com.hzu.homework.sysback.homework.vo.HomeworkModel;
import com.hzu.homework.sysback.like.service.LikeService;
import com.hzu.homework.sysback.stucourserel.service.StudentCourseRelService;
import com.hzu.homework.sysback.user.service.UserService;
import com.hzu.homework.sysback.user.vo.UserModel;

@Controller
@RequestMapping("sysback/homework")
public class HomeworkController {
	private HomeworkService myService;
	@Autowired
	private UserService userService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private LikeService likeService;
	@Autowired 
	private StudentCourseRelService stuRelService;
	@Autowired
	private CommentService commentService;
	@Autowired
	public void setMyService(HomeworkService bs) {
		this.myService=bs;
	}
	
	@RequestMapping("/homeworkList")
	public String studentList(Model model){	
		return "sysback/homework/homeworkList";
	}
	
	/**
	 * 课程列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/getHomeworkList" }, method = {RequestMethod.GET}, produces = { "text/html;charset=UTF-8" })
	public String getHomeworkList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<HomeworkModel> list = myService.getAll();
		for(HomeworkModel item:list) {
			UserModel teacher = userService.getByUuid(item.getTeacherUuid());
			item.setTeacherName(teacher.getLoginName());
			CourseModel course = courseService.getByUuid(item.getCourseUuid());
			int allNum =stuRelService.getStudentCountByCourseUuid(course.getUuid());
			item.setCourseName(course.getCourseName());
			item.setAllNum(allNum+"");
			int likeNum = likeService.getCount(item.getUuid());
			item.setLikeNum(likeNum);
			String state = item.getState();
			if(state.equals("1")) {
				item.setState("已收取");
			}else {
				item.setState("进行中");
			}
			if(commentService.getByHomeworkUuid(item.getUuid())!=null) {
				int commentNum = commentService.getByHomeworkUuid(item.getUuid()).size();
				item.setCommentNum(commentNum+"");
			}else {
				item.setCommentNum("0");
			}
			
		}
		return JSONArray.toJSONString(list);
	}

}
