package com.hzu.homework.sysback.stucourserel.dao;

import java.util.List;

import com.hzu.homework.sysback.stucourserel.vo.StudentCourseRelModel;


public interface StudentCourseRelDAO {
	public String create(StudentCourseRelModel stuCourel);
	public String update(StudentCourseRelModel stuCourel);
	public String delete(StudentCourseRelModel stuCourel);
	public StudentCourseRelModel getByUuid(String uuid);
	public List<StudentCourseRelModel> getAll();
	public List<String> getCourseByNo(String no);
	public List<String> getStudentNoByCourseUuid(String courseUuid);
	public int getStudentCountByCourseUuid(String courseUuid);
}
