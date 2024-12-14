package org.x1a0kang.compare.common.model;

import java.io.Serializable;

/**
 * @author dongruijun
 * @version V1.0
 * @date 2020-04-09 16:22:17
 * @project app-data
 * @description 接口返回状态值
 **/
public enum StatusEnum implements Serializable {
    /**
     * 未授权
     */
    Unauthorized(401, "401"),
    /**
     * 无权访问
     */
    Forbidden(403, "403"),
    /**
     * 未找到
     */
    NotFound(404, "404"),

    /**
     * 服务暂不可用
     */
    ServiceClose(-5, "服务暂不可用"),
    /**
     * 服务未处理
     */
    Nothing(-4, "服务未处理"),
    /**
     * 接口未找到
     */
    WrongInterfaceName(-3, "接口未找到"),
    /**
     * 服务异常
     */
    Error(-2, "服务异常"),
    /**
     * 失败
     */
    Failure(-1, "失败"),

    /**
     * 成功
     */
    OK(0, "成功"),

    /**
     * 非法IP
     */
    IllegalIp(2, "非法IP"),

    /**
     * 不能访问
     */
    NoAccess(3, "不能访问"),

    /**
     * 参数异常
     */
    ParamMissing(4, "参数异常"),
    /**
     * 用户Token验证失败
     */
    TokenFailed(5, "用户Token验证失败"),
    /**
     * 访问太频繁
     */
    RequestTooMuch(6, "访问太频繁"),
    /**
     * AppId为空
     */
    AppIdMissing(7, "AppId为空"),
    /**
     * RequestId为空
     */
    RequestIdMissing(8, "RequestId为空"),
    /**
     * 用户信息为空
     */
    UidMissing(9, "用户信息为空");


    /**
     * 状态值
     */
    private final int code;

    /**
     * 类型描述
     */
    private final String desc;

    StatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static StatusEnum valueOf(int code) {
        for (StatusEnum type : StatusEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
}
