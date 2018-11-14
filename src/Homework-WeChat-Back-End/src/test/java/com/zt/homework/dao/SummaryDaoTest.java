package com.zt.homework.dao;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.entity.Summary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SummaryDaoTest {

    @Autowired
    private SummaryDao summaryDao;

    @Test
    public void insertSummary() {
        Summary summary = new Summary();
        summary.setCourseId(1);
        summary.setSummaryName("summary");
        summary.setDownloadTime(DateUtil.Date2Timestamp(new Date()));

        int num = summaryDao.insertSummary(summary);
        assertEquals(1, num);
    }

    @Test
    public void updateSummary() {
        int num = summaryDao.updateSummary(DateUtil.Date2Timestamp(new Date()));
        assertEquals(1, num);
    }

    @Test
    public void hasSummary() {
        int num = summaryDao.hasSummary(1);
        assertEquals(1, num);
    }
}