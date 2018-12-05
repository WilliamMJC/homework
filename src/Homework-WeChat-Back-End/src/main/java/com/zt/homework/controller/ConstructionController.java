package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dto.Result;
import com.zt.homework.service.ConstructionService;
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
public class ConstructionController {
    @Value("${homeDir}")
    private String homeDir;

    @Autowired
    private ConstructionService constructionService;



    @GetMapping(value = "/construction/{courseId}/{taskId}")
    public ResponseEntity<Result> getConstruction(@PathVariable Integer courseId, @PathVariable Integer taskId) {
        Integer userId = AppContext.getCurrentUserId();
        constructionService.createConstruction(courseId, taskId, userId);
        return ResponseEntity.ok(ResultUtil.success());
    }

    @GetMapping(value = "/construction/zip/{courseId}/{taskId}")
    public ResponseEntity<InputStreamResource> getConstructionZip(@PathVariable Integer courseId, @PathVariable Integer taskId) throws IOException {
        String filePath = homeDir + courseId + File.separator + taskId + ".zip";

        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
//
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));

    }
}
