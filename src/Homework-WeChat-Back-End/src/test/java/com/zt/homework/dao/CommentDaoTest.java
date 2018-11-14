package com.zt.homework.dao;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    public void insertComment() {
        Comment comment = new Comment();
        comment.setTaskId(1);
        comment.setUserId(7);
        comment.setCommentUserId(1);
        comment.setCommentContent("this is a comment");
        comment.setCommentDate(DateUtil.Date2Timestamp(new Date()));

        int num = commentDao.insertComment(comment);
        assertEquals(1, num);
    }

    @Test
    public void deleteComment() {
        int num = commentDao.deleteComment(1, 7, 1);
        assertEquals(1, num);
    }
}