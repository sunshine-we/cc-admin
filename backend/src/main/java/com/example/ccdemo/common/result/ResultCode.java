package com.example.ccdemo.common.result;

import lombok.Getter;

/**
 * 统一响应码枚举
 *
 * @author ccDemo
 */
@Getter
public enum ResultCode {

    /** 成功 */
    SUCCESS(200, "操作成功"),

    /** 客户端错误 */
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    /** 服务端错误 */
    INTERNAL_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),

    /** 业务错误（1xxx） */
    USERNAME_OR_PASSWORD_ERROR(1001, "用户名或密码错误"),
    USER_NOT_EXIST(1002, "用户不存在"),
    USER_ALREADY_EXIST(1003, "用户已存在"),
    TOKEN_EXPIRED(1004, "Token 已过期，请重新登录"),
    TOKEN_INVALID(1005, "Token 无效"),
    OLD_PASSWORD_ERROR(1006, "原密码错误"),

    /** 参数校验错误（2xxx） */
    PARAM_VALID_ERROR(2001, "参数校验失败"),

    /** 业务操作错误（3xxx） */
    OPERATION_FAILED(3001, "操作失败"),
    DATA_NOT_EXIST(3002, "数据不存在"),
    DATA_EXIST(3003, "数据已存在");

    /** 状态码 */
    private final int code;

    /** 默认提示信息 */
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
