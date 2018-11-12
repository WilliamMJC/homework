package com.hzu.homework.sysback.course.dao;

import java.util.List;

import com.hzu.homework.sysback.course.vo.CourseModel;

public interface CourseDAO {
	public String create(CourseModel course);
	public void update(CourseModel course);
	public void delete(CourseModel course);
	public List<CourseModel> getAll();
	public CourseModel getByUuid(String uuid);
	public List<CourseModel> getByTeacherUuid(String teacherUuid);
	public String getUuidByTAndCN(String teacherUuid,String courseName);
}
