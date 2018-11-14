package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dto.Result;
import com.zt.homework.dto.UserDto;
import com.zt.homework.entity.User;
import com.zt.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PutMapping(value = "/user")
    public ResponseEntity<Result> updateUser(@RequestBody User user) {
        Integer userId = AppContext.getCurrentUserId();
        user.setUserId(userId);
        if(user.getWorkMail() != null && !user.getWorkMail().equals("")) {
            userService.testMailConnect(user.getWorkMail(), user.getWorkMailPwd());
        }
        return ResponseEntity.ok(ResultUtil.success(userService.updateUserInfo(user)));
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Result> getUserInfo() {
        Integer userId = AppContext.getCurrentUserId();
        return ResponseEntity.ok(ResultUtil.success(userService.queryUser(userId)));
    }


    @GetMapping(value = "/user/{courseId}/{userId}")
    public ResponseEntity<Result> getUserInfoByUserId(@PathVariable Integer courseId, @PathVariable Integer userId) {
        Integer currentUserId = AppContext.getCurrentUserId();
        UserDto userDto = userService.getUserInfo(userId, currentUserId, courseId);
        return ResponseEntity.ok(ResultUtil.success(userDto));
    }

}
