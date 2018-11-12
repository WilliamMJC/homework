package com.hzu.homework.sysback.like.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.like.dao.LikeDAO;
import com.hzu.homework.sysback.like.vo.LikeModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {
	private LikeDAO myDao;
	@Autowired
	public void setMyDao(LikeDAO dao) {
		this.myDao=dao;
	}

	@Override
	public String create(LikeModel like) {
		like.setUuid(RandomUtil.generateString(15));
		like.setCreateTime(DateUtil.getStringDate());
		like.setOprTime(DateUtil.getStringDate());
		like.setDelFlag("1");
		return myDao.create(like);
	}

	@Override
	public String update(LikeModel like) {
		// TODO Auto-generated method stub
		return myDao.update(like);
	}

	@Override
	public String delete(LikeModel like) {
		// TODO Auto-generated method stub
		return myDao.delete(like);
	}

	@Override
	public LikeModel getByUuid(String like) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(like);
	}

	@Override
	public List<LikeModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}

	@Override
	public boolean isLike(String homeworkUuid, String userUuid) {
		// TODO Auto-generated method stub
		return myDao.isLike(homeworkUuid, userUuid);
	}

	@Override
	public LikeModel getUpdateLike(String homeworkUuid, String userUuid) {
		// TODO Auto-generated method stub
		return myDao.getUpdateLike(homeworkUuid, userUuid);
	}

	@Override
	public int getCount(String homeworkUuid) {
		// TODO Auto-generated method stub
		return myDao.getCount(homeworkUuid);
	}

}
