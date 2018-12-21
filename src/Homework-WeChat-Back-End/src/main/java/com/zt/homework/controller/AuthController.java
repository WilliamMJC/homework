package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.dto.Result;
import com.zt.homework.dto.WechatAuthenticationResponse;
import com.zt.homework.service.AuthService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 获取token
     * @param param
     * @return acessToken
     */
    @PostMapping(value = "/auth")
    public ResponseEntity<Result>
    createAuthenticationToken(@RequestBody String param) {
        JSONObject o = JSONObject.fromObject(param);
        String code = o.getString("code");
        System.out.println("code="+code);
        WechatAuthenticationResponse jwtResponse = authService.wechatLogin(code);

        return ResponseEntity.ok(ResultUtil.success(jwtResponse));
    }

    @GetMapping(value = "/authStatus")
    public ResponseEntity<Result> checkAuthExpired() {
        return ResponseEntity.ok(ResultUtil.success("授权未过期"));
    }

}
