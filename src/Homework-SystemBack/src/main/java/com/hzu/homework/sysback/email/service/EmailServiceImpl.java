package com.hzu.homework.sysback.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzu.homework.sysback.email.dao.EmailDAO;

@Service
public class EmailServiceImpl implements EmailService {
	private EmailDAO myDao;
	@Autowired
	public void setMyDao(EmailDAO dao) {
		this.myDao=dao;
	}
}
