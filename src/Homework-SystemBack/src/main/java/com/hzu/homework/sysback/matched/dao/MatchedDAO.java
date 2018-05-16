package com.hzu.homework.sysback.matched.dao;

import java.util.List;

import com.hzu.homework.sysback.matched.vo.MatchedModel;


public interface MatchedDAO {
	public String create(MatchedModel matched);
	public String update(MatchedModel matched);
	public String delete(MatchedModel matched);
	public MatchedModel getByUuid(String uuid);
	public List<MatchedModel> getAll();
	public List<MatchedModel> getByUserUuid(String uuid);
	public boolean isMatched(String uuid,String toUuid);
}
