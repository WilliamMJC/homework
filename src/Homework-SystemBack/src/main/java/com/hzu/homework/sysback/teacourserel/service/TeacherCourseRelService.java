package com.hzu.homework.sysback.teacourserel.service;

import java.util.List;

import com.hzu.homework.sysback.teacourserel.vo.TeacherCourseRelModel;

public interface TeacherCourseRelService {
	public String create(TeacherCourseRelModel stuCourel);
	public String update(TeacherCourseRelModel stuCourel);
	public String delete(TeacherCourseRelModel stuCourel);
	public TeacherCourseRelModel getByUuid(String uuid);
	public List<TeacherCourseRelModel> getAll();
	public TeacherCourseRelModel getByCourseUuid(String courseUuid);
	public String getTeacherUuidByCourseUuid(String courseUuid);
}
