package com.zt.homework.dao;

import com.zt.homework.entity.SC;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SCDaoTest {

    @Autowired
    private SCDao scDao;

    @Test
    @Ignore
    public void insertSCs() {
        List<SC> scList = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            SC sc = new SC();
            sc.setCourseId(1);
            sc.setStuId(String.valueOf(i + 5));

            scList.add(sc);
        }
        int affectedNum = scDao.insertSCs(scList);
        assertEquals(20, affectedNum);
    }

    @Test
    public void insertSC() {
        SC sc = new SC();
        sc.setCourseId(4);
        sc.setStuId("1111");

        int affectedNum = scDao.insertSC(sc);
        assertEquals(1, affectedNum);
    }

    @Test
    public void deleteSCsByStuId() {
        int affectedNum = scDao.deleteSCsByStuId("11");
        assertEquals(1, affectedNum);
    }

    @Test
    public void deleteSCBySC() {
        SC sc = new SC();
        sc.setCourseId(1);
        sc.setStuId("10");
        int affectedNum = scDao.deleteSCBySC(sc);
        assertEquals(1, affectedNum);
    }

    @Test
    public void deleteSCsByCourseId() {
        int affctedNum = scDao.deleteSCsByCourseId(4);
        assertEquals(1, affctedNum);
    }

    @Test
    public void updateSCBySC() {
        SC sc = new SC();
        sc.setStuId("12");
        sc.setCourseId(1);
        int affectedNum = scDao.updateSCBySC("5555", sc);
        assertEquals(1, affectedNum);
    }

    @Test
    public void hasSC() {
        SC sc = new SC();
        sc.setStuId("13");
        sc.setCourseId(1);
        assertSame(1, scDao.hasSC(sc));
    }
}