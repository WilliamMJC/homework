package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.dao.HomeworkDao;
import com.zt.homework.dto.Result;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.AuthException;
import com.zt.homework.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class HomeworkController {
    @Value("${homeDir}")
    private String homeDir;

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private HomeworkService homeworkService;

    // 预览作业
    @GetMapping(value = "/homework/{courseId}/{taskId}/{userId}")
    public ResponseEntity<InputStreamResource> getHomework(@PathVariable Integer courseId, @PathVariable Integer taskId, @PathVariable Integer userId) throws IOException {
        String filename = homeworkDao.queryHomework(taskId, userId, courseId).getFileName();
        String filePath = homeDir + courseId + File.separator + taskId + File.separator + filename;


        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));

    }

    // 创建作业评价excel
    @GetMapping(value = "/homework/createEvaluation/{courseId}/{taskId}")
    public ResponseEntity<Result> createEvaluation(@PathVariable Integer courseId, @PathVariable Integer taskId) {
        try {
            homeworkService.createEvaluation(courseId, taskId);
        } catch (IOException e) {
            throw new AuthException(ResultEnum.MAIL_SENT_FAIL);
        }
        return ResponseEntity.ok(ResultUtil.success());
    }

    // 完成作业评价
    @GetMapping(value = "/homework/completeEvaluation/{courseId}/{taskId}")
    public ResponseEntity<Result> completeEvaluation(@PathVariable Integer courseId, @PathVariable Integer taskId) {
        try {
            homeworkService.completeEvaluation(taskId, courseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthException(ResultEnum.MAIL_SEARCH_FAIL);
        }
        return ResponseEntity.ok(ResultUtil.success());
    }
}
