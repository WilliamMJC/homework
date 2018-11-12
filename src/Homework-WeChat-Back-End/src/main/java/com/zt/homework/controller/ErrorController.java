package com.zt.homework.controller;

import com.zt.homework.dto.Result;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(value = "/unAuth")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Result> unAuth() {
        throw new AuthException(ResultEnum.UNAUTH);
    }

    @RequestMapping(value = "/authExpired")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Result> authExpired() {
        throw new AuthException(ResultEnum.AUTH_EXPIRED);
    }
}
