package com.wbd.enums;

public enum ErrorEnum
{
    SYSTEM_ERROR("00000","系统错误"),
    ILLEGAL_PARAMETER("00001","非法参数错误"),
    UNAUTHORIZED_ERROR("00002","未授权错误"),
    NOT_LOGIN("00003","未登录错误"),
    LOCK_ACQUIRED_TIMEOUT("00004","锁获取超时错误"),
    OUT_OF_USER_COUNT("00005","锁获取超时错误"),
    REFRESH_TOKEN("00006","TOKEN刷新失败"),
    ERROR_USER_PASSWPRD("00007","用户密码错误"),
    ERROR_USER_NAME("00008","用户不存在"),
    ERROR_USER_INUSE("00009","用户已存在错误"),
    LACK_USER_NAME("00010","缺少用户名错误"),
    LACK_USER_PASSWORD("00011","缺少密码错误"),
    LACK_ORG_ID("00012","缺少组织ID错误"),
    LACK_ORG_NAME("00013","缺少组织名称错误"),
    ERROR_ORG_INUSE("00014","组织已存在错误"),
    MODIFY_USER_NAME_INUSE("00015","修改的用户名已被其他用户使用"),
    ERROR_ORG_ID("00016","组织机构不存在"),
    MODIFY_ORG_NAME_INUSE("00017","修改的用户名已被其他用户使用");

    private String code;

    private String msg;

    ErrorEnum(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public String getCode()
    {
        return code;
    }

    public String getMsg()
    {
        return msg;
    }
}
