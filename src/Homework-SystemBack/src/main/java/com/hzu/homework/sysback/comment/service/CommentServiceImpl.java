package com.hzu.homework.sysback.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.comment.dao.CommentDAO;
import com.hzu.homework.sysback.comment.vo.CommentModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	private CommentDAO myDao;
	
	@Autowired
	public void setMyDao(CommentDAO dao) {
		this.myDao=dao;
	}

	@Override
	public String create(CommentModel comment) {
		// TODO Auto-generated method stub
		comment.setUuid(RandomUtil.generateString(15));
		comment.setCreateTime(DateUtil.getStringDate());
		comment.setOprTime(DateUtil.getStringDate());
		comment.setDelFlag("1");
		return myDao.create(comment);
	}

	@Override
	public void update(CommentModel comment) {
		myDao.update(comment);
	}

	@Override
	public void delete(CommentModel comment) {
		myDao.delete(comment);
		
	}

	@Override
	public List<CommentModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}

	@Override
	public CommentModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(uuid);
	}

	@Override
	public List<CommentModel> getByHomeworkUuid(String homeworkUuid) {
		// TODO Auto-generated method stub
		return myDao.getByHomeworkUuid(homeworkUuid);
	}
	
}
