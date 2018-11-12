package com.hzu.homework.sysback.worksturel.dao;

import java.util.List;

import com.hzu.homework.sysback.worksturel.vo.WorkStuRelModel;


public interface WorkStuRelDAO {
	public String create(WorkStuRelModel stuCourel);
	public String update(WorkStuRelModel stuCourel);
	public String delete(WorkStuRelModel stuCourel);
	public WorkStuRelModel getByUuid(String uuid);
	public List<WorkStuRelModel> getAll();
	public WorkStuRelModel getByHomeworkUuid(String homeworkUuid);
	public String getNoByuuid(String homeworkUuid);
	public boolean isSubmit(String homeworkUuid,String no);
}
