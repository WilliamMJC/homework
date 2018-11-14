package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.dto.Result;
import com.zt.homework.service.ConstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstructionController {
    @Autowired
    private ConstructionService constructionService;

    @GetMapping(value = "/construction/{courseId}/{taskId}")
    public ResponseEntity<Result> getConstruction(@PathVariable Integer courseId, @PathVariable Integer taskId) {
        constructionService.createConstruction(courseId, taskId);
        return ResponseEntity.ok(ResultUtil.success());
    }
}
