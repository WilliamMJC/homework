package com.hzu.homework.sysback.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("StudentController")
@RequestMapping("/sysback/student")
public class StudentController {
	
	
	/**
	 * 跳转到学生列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/toList")
	public String toList(Model model){
		
		return "sysback/student/studentList";
	}

}
