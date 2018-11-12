package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dto.Result;
import com.zt.homework.service.SCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SCController {

    @Autowired
    private SCService scService;

    /**
     * 初始化用户此课程允许加入的学号列表
     * @return
     */
    @GetMapping(value = "/sc")
    public ResponseEntity<Result> getInitSC(@RequestParam Integer courseId, @RequestParam String courseName) throws Exception {
        Integer userId = AppContext.getCurrentUserId();
        List<String> stuIdList = scService.getInitStuIdList(userId, courseName, courseId);
        return ResponseEntity.ok(ResultUtil.success(stuIdList));
    }

    @PutMapping(value = "/sc/{courseId}/{stuId}")
    public ResponseEntity<Result> updateSC(@RequestBody String newStuId, @PathVariable Integer courseId, @PathVariable String stuId) {
        scService.updateSC(courseId, stuId, newStuId);
        return ResponseEntity.ok(ResultUtil.success());
    }
}
