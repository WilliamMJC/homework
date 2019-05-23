package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dto.Result;
import com.zt.homework.service.CourseMemberService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseMemberController {
    @Autowired
    private CourseMemberService courseMemberService;

    @DeleteMapping(value = "/courseMember/{courseId}")
    public ResponseEntity<Result> quitCourse(@PathVariable Integer courseId) {
        Integer userId = AppContext.getCurrentUserId();
        courseMemberService.deleteCM(courseId, userId);
        return ResponseEntity.ok(ResultUtil.success());
    }

    @PutMapping(value = "/courseMember/{courseId}")
    public ResponseEntity<Result> joinCourse(@PathVariable Integer courseId) {
        courseMemberService.joinCourseByCourseId(courseId);
        return ResponseEntity.ok(ResultUtil.success());
    }

    @GetMapping(value = "/courseMember/{courseId}/{currPage}/{pageSize}")
    public ResponseEntity<Result> getCMByPage(@PathVariable Integer courseId, @PathVariable Integer currPage, @PathVariable Integer pageSize) {
        JSONArray userDtoList = courseMemberService.queryStuCMByPage(courseId, currPage, pageSize);
        return ResponseEntity.ok(ResultUtil.success(userDtoList));
    }
}
