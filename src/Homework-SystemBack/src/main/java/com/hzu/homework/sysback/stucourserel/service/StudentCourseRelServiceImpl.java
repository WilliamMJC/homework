package com.hzu.homework.sysback.stucourserel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.stucourserel.dao.StudentCourseRelDAO;
import com.hzu.homework.sysback.stucourserel.vo.StudentCourseRelModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;

@Service
@Transactional
public class StudentCourseRelServiceImpl implements StudentCourseRelService {
	private StudentCourseRelDAO myDao;
	@Autowired
	public void setMyDao(StudentCourseRelDAO dao) {
		this.myDao = dao;
	}
	@Override
	@Transactional
	public String create(StudentCourseRelModel stuCourel) {
		// TODO Auto-generated method stub
		stuCourel.setUuid(RandomUtil.generateString(15));
		stuCourel.setCreateTime(DateUtil.getStringDate());
		stuCourel.setOprTime(DateUtil.getStringDate());
		stuCourel.setDelFlag("1");
		myDao.create(stuCourel);
		return "ret";
	}
	@Override
	public String update(StudentCourseRelModel stuCourel) {
		// TODO Auto-generated method stub
		stuCourel.setOprTime(DateUtil.getStringDate());
		myDao.update(stuCourel);
		return "ret";
	}
	@Override
	public String delete(StudentCourseRelModel stuCourel) {
		// TODO Auto-generated method stub
		myDao.delete(stuCourel);
		return "ret";
	}
	@Override
	public StudentCourseRelModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(uuid);
	}
	@Override
	public List<StudentCourseRelModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}
	@Override
	public List<String> getCourseByNo(String no) {
		// TODO Auto-generated method stub
		return myDao.getCourseByNo(no);
	}
	@Override
	public List<String> getStudentNoByCourseUuid(String courseUuid) {
		// TODO Auto-generated method stub
		return myDao.getStudentNoByCourseUuid(courseUuid);
	}
	@Override
	public int getStudentCountByCourseUuid(String courseUuid) {
		// TODO Auto-generated method stub
		return myDao.getStudentCountByCourseUuid(courseUuid);
	}
	
}
