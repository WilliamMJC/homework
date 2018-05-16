package com.hzu.homework.sysback.comment.service;

import java.util.List;

import com.hzu.homework.sysback.comment.vo.CommentModel;

public interface CommentService {
	public String create(CommentModel comment);
	public void update(CommentModel comment);
	public void delete(CommentModel comment);
	public List<CommentModel> getAll();
	public CommentModel getByUuid(String uuid);
	public List<CommentModel> getByHomeworkUuid(String homeworkUuid);

}
