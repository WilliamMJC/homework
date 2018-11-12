package com.zt.homework.dao;

import com.zt.homework.entity.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceDao {
    /**
     * 插入资源记录
     * @param resource
     * @return
     */
    Integer insertResource(Resource resource);

    /**
     * 根据资源id查找资源
     * @param resourceId
     * @return
     */
    Resource queryResourceByResourceId(Integer resourceId);

    /**
     * 根据courseId查找资源
     * @param courseId
     * @return
     */
    List<Resource>  queryResourceByCourseId(Integer courseId);
}
