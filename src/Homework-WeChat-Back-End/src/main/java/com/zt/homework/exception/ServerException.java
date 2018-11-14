package com.zt.homework.exception;

import com.zt.homework.enums.ResultEnum;

public class ServerException extends RuntimeException {
    private  Integer code;

    public ServerException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
