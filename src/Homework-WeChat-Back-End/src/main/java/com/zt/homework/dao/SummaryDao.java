package com.zt.homework.dao;

import com.zt.homework.entity.Summary;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface SummaryDao {
    /**
     * 插入一条summary记录
     * @param summary
     * @return
     */
    Integer insertSummary(Summary summary);

    /**
     * 更新summary下载时间
     * @param downloadTime
     * @return
     */
    Integer updateSummary(Timestamp downloadTime);

    /**
     * 查询是否存在summary记录
     * @param courseId
     * @return
     */
    Integer hasSummary(Integer courseId);
}
