package com.hzu.homework.sysback.like.dao;

import java.util.List;
import com.hzu.homework.sysback.like.vo.LikeModel;

public interface LikeDAO {
	public String create(LikeModel like);
	public String update(LikeModel like);
	public String delete(LikeModel like);
	public LikeModel getByUuid(String like);
	public List<LikeModel> getAll();
	public boolean isLike(String homeworkUuid,String userUuid);
	public LikeModel getUpdateLike(String homeworkUuid,String userUuid);
	public int getCount(String homeworkUuid);

}
