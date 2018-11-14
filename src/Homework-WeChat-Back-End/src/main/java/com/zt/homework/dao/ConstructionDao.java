package com.zt.homework.dao;

import com.zt.homework.entity.Construction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ConstructionDao {
    /**
     * 插入一条附件记录
     * @param construction
     * @return
     */
    Integer insertConstruction(Construction construction);

    /**
     * 更新附件下载的时间
     * @param downloadTime
     * @param taskId
     * @param courseId
     * @return
     */
    Integer updateConstruction(@Param("downloadTime") Timestamp downloadTime, @Param("taskId") Integer taskId, @Param("courseId") Integer courseId);

    /**
     * 是否存在这个construction记录
     * @param taskId
     * @param courseId
     * @return
     */
    Integer hasConstruction(@Param("taskId") Integer taskId, @Param("courseId") Integer courseId);
}
