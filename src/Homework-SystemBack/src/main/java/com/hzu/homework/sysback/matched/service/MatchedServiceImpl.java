package com.hzu.homework.sysback.matched.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.matched.dao.MatchedDAO;
import com.hzu.homework.sysback.matched.vo.MatchedModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;

@Service
@Transactional
public class MatchedServiceImpl implements MatchedService {
	
	private MatchedDAO myDao;
	@Autowired
	public void setMyDao(MatchedDAO dao) {
		this.myDao=dao;
	}

	@Override
	public String create(MatchedModel matched) {
		// TODO Auto-generated method stub
		matched.setUuid(RandomUtil.generateString(15));
		matched.setCreateTime(DateUtil.getStringDate());
		matched.setOprTime(DateUtil.getStringDate());
		matched.setDelFlag("1");
		return myDao.create(matched);
	}

	@Override
	public String update(MatchedModel matched) {
		// TODO Auto-generated method stub
		return myDao.update(matched);
	}

	@Override
	public String delete(MatchedModel matched) {
		// TODO Auto-generated method stub
		return myDao.delete(matched);
	}

	@Override
	public MatchedModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(uuid);
	}

	@Override
	public List<MatchedModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}

	@Override
	public List<MatchedModel> getByUserUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUserUuid(uuid);
	}

	@Override
	public boolean isMatched(String uuid, String toUuid) {
		// TODO Auto-generated method stub
		return myDao.isMatched(uuid, toUuid);
	}
	


}
