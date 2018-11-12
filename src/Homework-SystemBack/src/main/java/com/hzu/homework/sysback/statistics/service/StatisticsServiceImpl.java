package com.hzu.homework.sysback.statistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzu.homework.sysback.statistics.dao.StatisticsDAO;
import com.hzu.homework.sysback.statistics.vo.StatisticsModel;
import com.hzu.homework.util.DateUtil;
import com.hzu.homework.util.RandomUtil;
@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {
	private StatisticsDAO myDao;
	@Autowired
	public void setMyDao(StatisticsDAO dao) {
		this.myDao=dao;
	}

	@Override
	public String create(StatisticsModel statistics) {
		statistics.setUuid(RandomUtil.generateString(15));
		statistics.setCreateTime(DateUtil.getStringDate());
		statistics.setReceiveTime(DateUtil.getStringDate());
		statistics.setDelFlag("1");
		return myDao.create(statistics);
	}

	@Override
	public String update(StatisticsModel statistics) {
		// TODO Auto-generated method stub
		return myDao.update(statistics);
	}

	@Override
	public String delete(StatisticsModel statistics) {
		// TODO Auto-generated method stub
		return myDao.delete(statistics);
	}

	@Override
	public StatisticsModel getByUuid(String uuid) {
		// TODO Auto-generated method stub
		return myDao.getByUuid(uuid);
	}

	@Override
	public List<StatisticsModel> getAll() {
		// TODO Auto-generated method stub
		return myDao.getAll();
	}

}
