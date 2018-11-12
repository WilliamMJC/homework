package com.zt.homework.enums;

public enum ResultEnum {
    UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(0, "请求成功"),

    UNAUTH(100, "非法用户请求"),
    AUTH_EXPIRED(101, "用户授权已过期"),
    PERMISSION_DENY(102, "没有相应的权限，请联系管理员"),
    OPERATION_FAIL(103, "操作失败"),

    NOT_FOUNDE(404, "请求的资源不存在"),

    SERVER_ERROR(500, "服务器错误"),

    MAIL_CONNECT_FAIL(600, "邮箱连接失败"),
    MAIL_WITHOUT_ATTACH_FILE(601, "邮箱不包含附件"),
    ATTACH_FILE_NOT_ACCEPT(602, "附件不符合要求"),
    MAIL_SEARCH_FAIL(603, "邮箱检索失败"),
    MAIL_SENT_FAIL(604, "作业邮件发送失败")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
