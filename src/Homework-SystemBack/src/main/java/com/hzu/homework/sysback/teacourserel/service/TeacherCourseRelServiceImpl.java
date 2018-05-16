package com.hzu.homework.sysback.teacourserel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.teacourserel.dao.TeacherCourseRelDAO;
import com.hzu.homework.sysback.teacourserel.vo.TeacherCourseRelModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;

@Service
@Transactional
public class TeacherCourseRelServiceImpl implements TeacherCourseRelService {
	
	private TeacherCourseRelDAO myDao;
	@Autowired
	public void  setMyDao(TeacherCourseRelDAO dao) {
		this.myDao = dao;
	}

	@Override
	public String create(TeacherCourseRelModel stuCourel) {
		stuCourel.setUuid(RandomUtil.generateString(15));
		stuCourel.setCreateTime(DateUtil.getStringDate());
		stuCourel.setOprTime(DateUtil.getStringDate());
		stuCourel.setDelFlag("1");
		System.out.println("创建教师关联表");
		return myDao.create(stuCourel);
	}

	@Override
	public String update(TeacherCourseRelModel stuCourel) {
		// TODO Auto-generated method stub
		stuCourel.setOprTime(DateUtil.getStringDate());
		return myDao.update(stuCourel);
	}

	@Override
	public String delete(TeacherCourseRelModel stuCourel) {
		// TODO Auto-generated method stub
		return myDao.delete(stuCourel);
	}

	@Override
	public TeacherCourseRelModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(uuid);
	}

	@Override
	public List<TeacherCourseRelModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}

	@Override
	public TeacherCourseRelModel getByCourseUuid(String courseUuid) {
		// TODO Auto-generated method stub
		return myDao.getByCourseUuid(courseUuid);
	}

	@Override
	public String getTeacherUuidByCourseUuid(String courseUuid) {
		// TODO Auto-generated method stub
		return myDao.getTeacherUuidByCourseUuid(courseUuid);
	}

	
}
