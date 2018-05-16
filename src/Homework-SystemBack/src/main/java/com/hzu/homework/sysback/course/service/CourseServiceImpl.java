package com.hzu.homework.sysback.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.course.dao.CourseDAO;
import com.hzu.homework.sysback.course.vo.CourseModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;


@Service
@Transactional
public class CourseServiceImpl implements CourseService {
	private CourseDAO myDao;
	
	@Autowired
	public void setMyDao(CourseDAO dao) {
		this.myDao=dao;
	}

	@Override
	public String create(CourseModel course) {
		//course.setUuid(RandomUtil.generateString(15));
		course.setCreateTime(DateUtil.getStringDate());
		course.setOprTime(DateUtil.getStringDate());
		course.setDelFlag("1");
		return myDao.create(course);
	}

	@Override
	public void update(CourseModel course) {
		// TODO Auto-generated method stub
		course.setOprTime(DateUtil.getStringDate());
		myDao.update(course);
	}

	@Override
	public void delete(CourseModel course) {
		// TODO Auto-generated method stub
		course.setDelFlag("0");
		myDao.delete(course);

	}

	@Override
	public List<CourseModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}

	@Override
	public CourseModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(uuid);
	}

	@Override
	public List<CourseModel> getByTeacherUuid(String teacherUuid) {
		// TODO Auto-generated method stub
		return myDao.getByTeacherUuid(teacherUuid);
	}

	@Override
	public String getUuidByTAndCN(String teacherUuid, String courseName) {
		// TODO Auto-generated method stub
		return myDao.getUuidByTAndCN(teacherUuid, courseName);
	}

}
