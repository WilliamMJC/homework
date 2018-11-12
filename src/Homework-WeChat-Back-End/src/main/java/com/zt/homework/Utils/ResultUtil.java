package com.zt.homework.Utils;

import com.zt.homework.dto.Result;

public class ResultUtil {

    public static Result success(Object data) {
        Result result = new Result<>();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(data);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
