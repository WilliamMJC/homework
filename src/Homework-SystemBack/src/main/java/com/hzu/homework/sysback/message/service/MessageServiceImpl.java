package com.hzu.homework.sysback.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.message.dao.MessageDAO;
import com.hzu.homework.sysback.message.vo.MessageModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
	private MessageDAO myDao;
	@Autowired
	public void setMyDao(MessageDAO dao) {
		this.myDao=dao;
	}
	
	@Override
	public String create(MessageModel message) {
		message.setUuid(RandomUtil.generateString(15));
		message.setCreateTime(DateUtil.getStringDate());
		message.setOprTime(DateUtil.getStringDate());
		message.setDelFlag("1");
		message.setState("0");
		return myDao.create(message);
	}
	@Override
	public String update(MessageModel message) {
		message.setState("1");
		return myDao.update(message);
	}
	@Override
	public String delete(MessageModel message) {
		return myDao.delete(message);
	}
	@Override
	public MessageModel getByUuid(String uuid) {
		return myDao.getByUuid(uuid);
	}
	@Override
	public List<MessageModel> getAll() {
		return myDao.getAll();
	}
	@Override
	public MessageModel getLastMessage(String belongUuid, String toUuid) {
		return myDao.getLastMessage(belongUuid, toUuid);
	}
	@Override
	public List<MessageModel> getListByBelongUuid(String belongUuid, String toUuid) {
		// TODO Auto-generated method stub
		return myDao.getListByBelongUuid(belongUuid, toUuid);
	}
	@Override
	public MessageModel getNewMessage(String belongUuid, String formUuid) {
		// TODO Auto-generated method stub
		return myDao.getNewMessage(belongUuid, formUuid);
	}

	@Override
	public int getAllUnreadNum(String belongUuid) {
		// TODO Auto-generated method stub
		return myDao.getAllUnreadNum(belongUuid);
	}

	@Override
	public int getUnreadNum(String belongUuid, String formUuid) {
		// TODO Auto-generated method stub
		return myDao.getUnreadNum(belongUuid, formUuid);
	}
	
}
