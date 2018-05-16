package com.hzu.homework.sysback.comment.dao;

import java.util.List;

import com.hzu.homework.sysback.comment.vo.CommentModel;


public interface CommentDAO {
	public String create(CommentModel comment);
	public void update(CommentModel comment);
	public void delete(CommentModel comment);
	public List<CommentModel> getAll();
	public CommentModel getByUuid(String uuid);
	public List<CommentModel> getByHomeworkUuid(String homeworkUuid);

}
