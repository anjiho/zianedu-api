package com.zianedu.api.define.err;

public class ZianException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "No message";

    private final int errCode;

    private String message = "";

    public ZianException(int errCode) {
        super(DEFAULT_MESSAGE);
        this.errCode = errCode;
        this.message = DEFAULT_MESSAGE;
    }

    public ZianException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
        this.message = message;
    }

    public ZianException(ZianErrCode zianErrCode) {
        super(zianErrCode.msg());
        this.errCode = zianErrCode.code();
        this.message = zianErrCode.msg();
    }

    public ZianException(ZianErrCode zianErrCode, String message) {
        this(zianErrCode.code(), message);
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
