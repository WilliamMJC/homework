package com.hzu.homework.sysback.statistics.service;

import java.util.List;

import com.hzu.homework.sysback.statistics.vo.StatisticsModel;

public interface StatisticsService {
	public String create(StatisticsModel statistics);
	public String update(StatisticsModel statistics);
	public String delete(StatisticsModel statistics);
	public StatisticsModel getByUuid(String uuid);
	public List<StatisticsModel> getAll();

}
