package com.hzu.homework.sysback.user.dao;

import java.util.List;

import com.hzu.homework.sysback.user.vo.UserModel;

public interface UserDAO {
	
	public String create(UserModel user);
	public String update(UserModel user);
	public String delete(UserModel user);
	public UserModel getByUuid(String uuid);
	public List<UserModel> getAll();
	public String getUserType(String uuid);
	public boolean checkUserName(String userName);
	public boolean checkEmail(String email);
	public String checkUserType(String uuid);
	public boolean checkLogin(String loginName,String password);
	public UserModel getLoginModel(String loginName,String password);
	public List<UserModel> getListByType(String type);
	/**
	 * 根据学号获取学生名称
	 * @param string
	 * @return
	 */
	public String getStudentByNo(String no);
}
