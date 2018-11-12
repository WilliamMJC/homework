package com.zt.homework.handler;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.dto.Result;
import com.zt.homework.exception.AuthException;
import com.zt.homework.exception.MailException;
import com.zt.homework.exception.ResourceNotFoundException;
import com.zt.homework.exception.ServerException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 权限异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthException.class)
    public Result handleAuthException(Exception e) {
        AuthException authException = (AuthException) e;
        return ResultUtil.error(authException.getCode(), authException.getMessage());
    }

    /**
     * 服务器错误
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServerException.class)
    public Result handleServerException(Exception e) {
        ServerException serverException = (ServerException) e;
        return ResultUtil.error(serverException.getCode(), serverException.getMessage());
    }

    /**
     * 邮箱错误
     * @param e
     * @return
     */
    @ExceptionHandler(value = MailException.class)
    public Result handleMailException(Exception e) {
        MailException mailException = (MailException) e;
        return ResultUtil.error(mailException.getCode(), mailException.getMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public Result handleResouceNotFoundException(Exception e) {
        ResourceNotFoundException resourceNotFoundException = (ResourceNotFoundException) e;
        return ResultUtil.error(resourceNotFoundException.getCode(), resourceNotFoundException.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();

        if(e instanceof AuthException) {
            AuthException authException = (AuthException) e;
            return ResultUtil.error(authException.getCode(), authException.getMessage());
        } else if(e instanceof ServerException) {
            ServerException serverException = (ServerException) e;
            return ResultUtil.error(serverException.getCode(), serverException.getMessage());
        } else if(e instanceof MailException) {
            MailException mailException = (MailException) e;
            return ResultUtil.error(mailException.getCode(), mailException.getMessage());
        } else if(e instanceof ResourceNotFoundException) {
            ResourceNotFoundException resourceNotFoundException = (ResourceNotFoundException) e;
            return ResultUtil.error(resourceNotFoundException.getCode(), resourceNotFoundException.getMessage());
        } else {
            return ResultUtil.error(-1, "未知错误");
        }
    }
}
