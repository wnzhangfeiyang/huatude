package com.mxgraph.response;

public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILURE(400, "网络异常");


    final int code;
    final String message;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private ResultCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
