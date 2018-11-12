package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dto.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity<Result> test() {
        return ResponseEntity.ok(ResultUtil.success());
    }

    @GetMapping("/testAuth")
    public Integer testAuth() {
        return AppContext.getCurrentUserId();
    }

}
