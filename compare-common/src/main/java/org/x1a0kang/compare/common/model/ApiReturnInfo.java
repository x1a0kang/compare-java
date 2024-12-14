package org.x1a0kang.compare.common.model;

import java.io.Serializable;

/**
 * @author dongruijun
 * @version V1.0
 * @date 2020-04-09 14:12:53
 * @project app-data
 * @description 返回值
 **/
public class ApiReturnInfo implements Serializable {
    private String requestId;
    private String message;
    private int status;
    private int code;
    private Object data;
    private Object stack;

    /**
     * 全局Id
     * 作用：接口调用方生成的唯一id（如GUID），接口会入库并原样返回，方便后期做业务流程查询；
     */
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * 接口返回信息
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 接口返回码，0：ok,-1：Failure(只有成功失败两种状态)
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 接口状态码
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 业务数据，Json格式
     */
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 透传第三方接口返回值，或者错误信息
     */
    public Object getStack() {
        return stack;
    }

    public void setStack(Object value) {
        stack = value;
    }

    public ApiReturnInfo() {
    }

    public ApiReturnInfo(StatusEnum status, String message) {
        setCode(status.getCode());
        setStatus(status.getCode() == StatusEnum.OK.getCode() ? StatusEnum.OK.getCode() : StatusEnum.Failure.getCode());
        setMessage(message);
        setData(null);
        setStack(null);
    }

    public ApiReturnInfo(StatusEnum status, String message, String requestId) {
        setRequestId(requestId);
        setCode(status.getCode());
        setStatus(status.getCode() == StatusEnum.OK.getCode() ? StatusEnum.OK.getCode() : StatusEnum.Failure.getCode());
        setMessage(message);
        setData(null);
        setStack(null);
    }

    public ApiReturnInfo(Object data, StatusEnum status, String message) {
        setCode(status.getCode());
        setStatus(status.getCode() == StatusEnum.OK.getCode() ? StatusEnum.OK.getCode() : StatusEnum.Failure.getCode());
        setMessage(message);
        setData(data);
        setStack(null);
    }

    public ApiReturnInfo(Object data, StatusEnum status, String message, String requestId) {
        setRequestId(requestId);
        setCode(status.getCode());
        setStatus(status.getCode() == StatusEnum.OK.getCode() ? StatusEnum.OK.getCode() : StatusEnum.Failure.getCode());
        setMessage(message);
        setData(data);
        setStack(null);
    }

    public static ApiReturnInfo getNothing() {
        return new ApiReturnInfo(StatusEnum.Failure, StatusEnum.Nothing, "Nothing");
    }

    public static ApiReturnInfo getSuccess() {
        String message = "OK";
        return new ApiReturnInfo(null, StatusEnum.OK, message);
    }

    public static ApiReturnInfo getSuccess(Object data) {
        String message = "OK";
        if (null == data) {
            message = "NoData";
        }
        return new ApiReturnInfo(data, StatusEnum.OK, message);
    }

    public static ApiReturnInfo getSuccess(Object data, String requestId) {
        String message = "OK";
        if (null == data) {
            message = "NoData";
        }
        return new ApiReturnInfo(data, StatusEnum.OK, message, requestId);
    }

    public static ApiReturnInfo getSuccess(Object data, String message, String requestId) {
        return new ApiReturnInfo(data, StatusEnum.OK, message, requestId);
    }

    public static ApiReturnInfo getFailure() {
        return getFailure("Failure");
    }

    public static ApiReturnInfo getFailure(String message) {
        return new ApiReturnInfo(null, StatusEnum.Failure, message);
    }

    public static ApiReturnInfo getFailure(String message, String requestId) {
        return new ApiReturnInfo(null, StatusEnum.Failure, message, requestId);
    }

    public static ApiReturnInfo getParamMissing() {
        return getParamMissing("Failure");
    }

    public static ApiReturnInfo getParamMissing(String message) {
        return new ApiReturnInfo(null, StatusEnum.ParamMissing, message);
    }

    public static ApiReturnInfo getTokenFailed(String message) {
        return new ApiReturnInfo(null, StatusEnum.TokenFailed, message);
    }

    public static ApiReturnInfo getTokenFailed() {
        return new ApiReturnInfo(null, StatusEnum.TokenFailed, StatusEnum.TokenFailed.getDesc());
    }

    public static ApiReturnInfo getError() {
        return getError("Error");
    }

    public static ApiReturnInfo getError(String message) {
        return new ApiReturnInfo(null, StatusEnum.Error, message);
    }

    public static ApiReturnInfo getError(String message, String requestId) {
        return new ApiReturnInfo(null, StatusEnum.Error, message, requestId);
    }

    public static ApiReturnInfo getUnauthorized() {
        return getUnauthorized("Unauthorized");
    }

    public static ApiReturnInfo getUnauthorized(String message) {
        return new ApiReturnInfo(null, StatusEnum.Unauthorized, message);
    }

    public static ApiReturnInfo getForbidden() {
        return getForbidden("Forbidden");
    }

    public static ApiReturnInfo getForbidden(String message) {
        return new ApiReturnInfo(null, StatusEnum.Forbidden, message);
    }

    public static ApiReturnInfo getNotFound() {
        return new ApiReturnInfo(null, StatusEnum.NotFound, "NotFound");
    }

    public static ApiReturnInfo getNotFound(String message) {
        return new ApiReturnInfo(null, StatusEnum.NotFound, message);
    }

    public static ApiReturnInfo getServiceClose() {
        return getServiceClose(StatusEnum.ServiceClose.getDesc());
    }

    public static ApiReturnInfo getServiceClose(String message) {
        return new ApiReturnInfo(null, StatusEnum.ServiceClose, message);
    }

    public static ApiReturnInfo getNoAccess() {
        return getServiceClose(StatusEnum.NoAccess.getDesc());
    }

    public static ApiReturnInfo getNoAccess(String message) {
        return new ApiReturnInfo(null, StatusEnum.NoAccess, message);
    }
}