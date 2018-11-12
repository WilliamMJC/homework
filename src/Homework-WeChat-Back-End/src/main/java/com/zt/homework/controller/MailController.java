package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dto.HomeworkDto;
import com.zt.homework.dto.Result;
import com.zt.homework.service.HomeworkService;
import com.zt.homework.service.MailReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MailController {
    @Autowired
    private MailReceiveService mailReceiveService;

    @Autowired
    private HomeworkService homeworkService;

    /**
     * 更新邮件服务器
     * @param courseId
     * @param taskId
     * @return
     */
    @GetMapping(value = "/mail/{courseId}/{taskId}")
    public ResponseEntity<Result> updateMail(@PathVariable Integer courseId, @PathVariable Integer taskId) {
        Integer userId = AppContext.getCurrentUserId();
        mailReceiveService.queryMailByTask(taskId, courseId);
        List<HomeworkDto> homeworkDtoList = homeworkService.getTaskHomeworkDtoList(taskId, courseId, userId);
        return ResponseEntity.ok(ResultUtil.success(homeworkDtoList));
    }


}
