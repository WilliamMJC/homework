package com.hzu.homework.sysback.message.dao;

import java.util.List;

import com.hzu.homework.sysback.message.vo.MessageModel;


public interface MessageDAO {
	public String create(MessageModel message);
	public String update(MessageModel message);
	public String delete(MessageModel message);
	public MessageModel getByUuid(String uuid);
	public List<MessageModel> getAll();
	public MessageModel getLastMessage(String belongUuid,String toUuid);
	public List<MessageModel> getListByBelongUuid(String belongUuid,String toUuid);
	public MessageModel getNewMessage(String belongUuid,String formUuid);
	public int getAllUnreadNum(String belongUuid);
	public int getUnreadNum(String belongUuid,String formUuid);
}
