package com.hzu.homework.sysback.sysaccount.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzu.homework.sysback.sysaccount.dao.SysAccountDao;
import com.hzu.homework.sysback.sysaccount.vo.SysAccountModel;
import com.hzu.homework.sysback.sysaccount.service.SysAccountService;


@Service
@Transactional
public class SysAccountServiceImpl implements SysAccountService {
	
	@Autowired
	private SysAccountDao sysAccountDao;

	@Override
	@Transactional
	public boolean login(String username, String password) {
		boolean flag = false;
		SysAccountModel user = this.sysAccountDao.findByUsernameAndPassword(username, password);
		if(user != null){
			if(username.equals(user.getLoginName()) && password.equals(user.getPassword())){
				flag = true;
			}
		}
		return flag;
	}

}
