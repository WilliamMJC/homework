package com.hzu.homework.sysback.user.service;

import java.util.List;

import com.hzu.homework.sysback.user.vo.UserModel;

public interface UserService {
	public String create(UserModel user);
	public String update(UserModel user);
	public String delete(UserModel user);
	public UserModel getByUuid(String uuid);
	public String getUserType(String uuid);
	public List<UserModel> getAll();
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
