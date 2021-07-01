package com.mxgraph.response;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * json 返回值
 */
public class R<T> implements Serializable {
    /**
     * 返回状态值
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 承载数据
     */
    private T data;

    /**
     * 返回消息
     */
    private String msg;


    public R(int code, T data, String msg) {
        this.code = code;
        this.success = ResultCode.SUCCESS.getCode() == code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> R<T> data(T data) {
        return data(data, "操作成功");
    }

    private static <T> R<T> data(T data, String msg) {
        return data(200, data, msg);
    }

    private static <T> R<T> data(int code, T data, String msg) {
        return new R<T>(code, data, data == null ? "暂无承载数据" : msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(new R(this.code, this.data, this.msg));
    }

    public R() {
    }
}
