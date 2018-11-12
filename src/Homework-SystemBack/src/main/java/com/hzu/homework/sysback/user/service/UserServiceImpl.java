package com.hzu.homework.sysback.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.user.dao.UserDAO;
import com.hzu.homework.sysback.user.vo.UserModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.MD5Util;
import com.hzu.homework.util.RandomUtil;

@Transactional
@Service
public class UserServiceImpl implements UserService{
	
	private UserDAO myDao;
	
	@Autowired
	public void setMyDao(UserDAO dao) {
		this.myDao = dao;
	}

	@Override
	@Transactional
	public String create(UserModel user) {
		user.setUuid(RandomUtil.generateString(15));
		user.setCreateTime(DateUtil.getStringDate());
		user.setOprTime(DateUtil.getStringDate());
		user.setUserType("0");
		user.setDelFlag("1");
		return myDao.create(user);
	}

	@Override
	public String update(UserModel user) {
		user.setOprTime(DateUtil.getStringDate());
		myDao.update(user);
		return "ret";
	}

	@Override
	public String delete(UserModel user) {
		myDao.delete(user);
		return "ret";
	}

	@Override
	public String getUserType(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getUserType(uuid);
	}

	@Override
	public UserModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(uuid);
	}

	@Override
	public List<UserModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}

	@Override
	public boolean checkUserName(String userName) {
		// TODO Auto-generated method stub
		return myDao.checkUserName(userName);
	}

	@Override
	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		return myDao.checkEmail(email);
	}

	@Override
	public String checkUserType(String uuid) {
		// TODO Auto-generated method stub
		return myDao.checkUserType(uuid);
	}

	@Override
	public boolean checkLogin(String loginName, String password) {
		// TODO Auto-generated method stub
		String loginPwd=MD5Util.md5(password);
		return myDao.checkLogin(loginName, loginPwd);
	}

	@Override
	public UserModel getLoginModel(String loginName, String password) {
		// TODO Auto-generated method stub
		String loginPwd=MD5Util.md5(password);
		return myDao.getLoginModel(loginName, loginPwd);
	}

	@Override
	public List<UserModel> getListByType(String type) {
		// TODO Auto-generated method stub
		return myDao.getListByType(type);
	}

	@Override
	public String getStudentByNo(String no) {
		// TODO Auto-generated method stub
		return myDao.getStudentByNo(no);
	}

}
