package com.hzu.homework.sysback.worksturel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.teacourserel.dao.TeacherCourseRelDAO;
import com.hzu.homework.sysback.teacourserel.vo.TeacherCourseRelModel;
import com.hzu.homework.sysback.worksturel.dao.WorkStuRelDAO;
import com.hzu.homework.sysback.worksturel.vo.WorkStuRelModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;

@Service
@Transactional
public class WorkStuRelServiceImpl implements WorkStuRelService {
	
	private WorkStuRelDAO myDao;
	@Autowired
	public void  setMyDao(WorkStuRelDAO dao) {
		this.myDao = dao;
	}

	@Override
	public String create(WorkStuRelModel stuCourel) {
		stuCourel.setUuid(RandomUtil.generateString(15));
		stuCourel.setCreateTime(DateUtil.getStringDate());
		stuCourel.setOprTime(DateUtil.getStringDate());
		stuCourel.setDelFlag("1");
		System.out.println("创建作业学号提交记录表");
		return myDao.create(stuCourel);
	}

	@Override
	public String update(WorkStuRelModel stuCourel) {
		// TODO Auto-generated method stub
		stuCourel.setOprTime(DateUtil.getStringDate());
		return myDao.update(stuCourel);
	}

	@Override
	public String delete(WorkStuRelModel stuCourel) {
		// TODO Auto-generated method stub
		return myDao.delete(stuCourel);
	}

	@Override
	public WorkStuRelModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(uuid);
	}

	@Override
	public List<WorkStuRelModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}

	@Override
	public WorkStuRelModel getByHomeworkUuid(String homeworkUuid) {
		// TODO Auto-generated method stub
		return myDao.getByHomeworkUuid(homeworkUuid);
	}

	@Override
	public String getNoByuuid(String homeworkUuid) {
		// TODO Auto-generated method stub
		return myDao.getNoByuuid(homeworkUuid);
	}

	@Override
	public boolean isSubmit(String homeworkUuid, String no) {
		// TODO Auto-generated method stub
		return myDao.isSubmit(homeworkUuid, no);
	}

}
