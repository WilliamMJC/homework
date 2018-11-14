package com.zt.homework.dao;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.entity.Resource;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceDaoTest {

    @Autowired
    private ResourceDao resourceDao;

    @Test
    @Ignore
    public void insertResource() {
        Resource resource = new Resource();
        resource.setCourseId(1);
        resource.setResourceName("test resource");
        resource.setResourceSize("15k");
        resource.setUploadTime(DateUtil.Date2Timestamp(new Date()));

        int affectedNum = resourceDao.insertResource(resource);
        assertEquals(1, affectedNum);
    }

    @Test
    public void queryResourceByResourceId() {
        Resource resource = resourceDao.queryResourceByResourceId(1);
        assertEquals("15k", resource.getResourceSize());
    }

    @Test
    public void queryResourceByCourseId() {
        List<Resource> resources = resourceDao.queryResourceByCourseId(1);
        assertEquals(1, resources.size());
    }
}