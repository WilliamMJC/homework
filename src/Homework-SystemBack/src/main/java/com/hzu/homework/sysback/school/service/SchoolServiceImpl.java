package com.hzu.homework.sysback.school.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.school.dao.SchoolDAO;
import com.hzu.homework.sysback.school.vo.SchoolModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {
	private SchoolDAO myDao;
	@Autowired
	public void setMyDao(SchoolDAO dao) {
		this.myDao=dao;
	}
	@Override
	public String create(SchoolModel school) {
		school.setUuid(RandomUtil.generateString(15));
		school.setCreateTime(DateUtil.getStringDate());
		school.setDelFlag("1");
		return myDao.create(school);
	}
	@Override
	public String update(SchoolModel school) {		
		myDao.update(school);
		return "ret";
	}
	@Override
	public String delete(SchoolModel school) {
		myDao.delete(school);
		return "ret";
	}
	@Override
	public SchoolModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(uuid);
	}
	@Override
	public List<SchoolModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}
	@Override
	public boolean checkSchoolName(String schoolName) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getUuidByName(String schoolName) {
		// TODO Auto-generated method stub
		return myDao.getUuidByName(schoolName);
	}
	
	
}
