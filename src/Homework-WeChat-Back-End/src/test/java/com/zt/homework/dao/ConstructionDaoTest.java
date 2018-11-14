package com.zt.homework.dao;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.entity.Construction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConstructionDaoTest {

    @Autowired
    private ConstructionDao constructionDao;

    @Test
    public void insertConstruction() {
        Construction construction = new Construction();
        construction.setTaskId(1);
        construction.setConstructionName("constructionName");
        construction.setDownloadTime(DateUtil.Date2Timestamp(new Date()));

        int num = constructionDao.insertConstruction(construction);
        assertEquals(1, num);
    }

    @Test
    public void updateDownloadTime() {
//        int num = constructionDao.updateConstruction(DateUtil.Date2Timestamp(new Date()));
//        assertEquals(1, num);
    }

    @Test
    public void hasConstruction() {
//        int num = constructionDao.hasConstruction(2);
//        assertEquals(0, num);
    }
}