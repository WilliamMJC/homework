package com.hzu.homework.sysback.worksturel.service;

import java.util.List;

import com.hzu.homework.sysback.teacourserel.vo.TeacherCourseRelModel;
import com.hzu.homework.sysback.worksturel.vo.WorkStuRelModel;

public interface WorkStuRelService {
	public String create(WorkStuRelModel stuCourel);
	public String update(WorkStuRelModel stuCourel);
	public String delete(WorkStuRelModel stuCourel);
	public WorkStuRelModel getByUuid(String uuid);
	public List<WorkStuRelModel> getAll();
	public WorkStuRelModel getByHomeworkUuid(String homeworkUuid);
	public String getNoByuuid(String homeworkUuid);
	public boolean isSubmit(String homeworkUuid,String no);
}
