package com.hzu.homework.sysback.homework.dao;

import java.util.List;

import com.hzu.homework.sysback.homework.vo.HomeworkModel;

public interface HomeworkDAO {
	public String create(HomeworkModel homework);
	public String update(HomeworkModel homework);
	public String delete(HomeworkModel homework);
	public HomeworkModel getByUuid(String homework);
	public List<HomeworkModel> getAll();
	public List<HomeworkModel> getByTeacherUuid(String teacherUuid);
	public HomeworkModel getLastHomework(String teacherUuid,String courseUuid);
	/**
	 * 通过课程uuid 查询作业次数
	 * @param uuid
	 * @return
	 */
	public int getCountByCourseUuid(String uuid);

}
