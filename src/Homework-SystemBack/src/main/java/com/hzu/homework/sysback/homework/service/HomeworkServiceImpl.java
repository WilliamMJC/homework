package com.hzu.homework.sysback.homework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.homework.dao.HomeworkDAO;
import com.hzu.homework.sysback.homework.vo.HomeworkModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;

@Service
@Transactional
public class HomeworkServiceImpl implements HomeworkService {
	private HomeworkDAO myDao;
	@Autowired
	public void setMyDao(HomeworkDAO dao) {
		this.myDao=dao;
	}
	@Override
	public String create(HomeworkModel homework) {
		// TODO Auto-generated method stub
		homework.setUuid(RandomUtil.generateString(15));
		homework.setCreateTime(DateUtil.getStringDate());
		homework.setOprTime(DateUtil.getStringDate());
		homework.setUpdateTime(DateUtil.getStringDate());
		homework.setDelFlag("1");
		homework.setSubmittedNum("0");
		homework.setState("0");
		return myDao.create(homework);
	}
	@Override
	public String update(HomeworkModel homework) {
		// TODO Auto-generated method stub
		homework.setOprTime(DateUtil.getStringDate());
		homework.setUpdateTime(DateUtil.getStringDate());
		return myDao.update(homework);
	}
	@Override
	public String delete(HomeworkModel homework) {
		// TODO Auto-generated method stub
		return myDao.delete(homework);
	}
	@Override
	public HomeworkModel getByUuid(String homework) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(homework);
	}
	@Override
	public List<HomeworkModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}
	@Override
	public List<HomeworkModel> getByTeacherUuid(String teacherUuid) {
		// TODO Auto-generated method stub
		return myDao.getByTeacherUuid(teacherUuid);
	}
	@Override
	public HomeworkModel getLastHomework(String teacherUuid, String courseUuid) {
		// TODO Auto-generated method stub
		return myDao.getLastHomework(teacherUuid, courseUuid);
	}
	@Override
	public int getCountByCourseUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getCountByCourseUuid(uuid);
	}
	
}
