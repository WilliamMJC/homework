package com.zt.homework.dao;

import com.zt.homework.entity.SC;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SCDao {
    /**
     * 插入多天sc记录
     * @return
     */
    Integer insertSCs(List<SC> scList);

    /**
     * 插入一条sc记录
     * @param sc
     * @return
     */
    Integer insertSC(SC sc);

    /**
     * 根据stuId删除该学号所有的选课记录
     * @param stuId
     * @return
     */
    Integer deleteSCsByStuId(String stuId);

    /**
     * 根据sc删除一条具体的选课记录
     * @param sc
     * @return
     */
    Integer deleteSCBySC(SC sc);

    /**
     * 根据课程id删除该课程的选课记录
     * @param courseId
     * @return
     */
    Integer deleteSCsByCourseId(Integer courseId);

    /**
     * 根据sc修改一条sc记录
     * @param newStuId sc
     * @return
     */
    Integer updateSCBySC(@Param("newStuId") String newStuId, @Param("sc") SC sc);

    /**
     * 查询是否存在这条选课记录
     * @param sc
     * @return
     */
    Integer hasSC(SC sc);

    /**
     * 根据课程id查找sc
     * @param courseId
     * @return
     */
    List<SC> querySCByCourseId(Integer courseId);
}
