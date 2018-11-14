package com.zt.homework.dao;

import com.zt.homework.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao {
    /**
     * 插入一条评论
     * @param comment
     * @return
     */
    Integer insertComment(Comment comment);

    /**
     * 删除一条评论
     * @param taskId
     * @param userId
     * @param commentUserId
     * @return
     */
    Integer deleteComment(@Param("taskId") Integer taskId, @Param("userId") Integer userId, @Param("commentUserId") Integer commentUserId);
}
