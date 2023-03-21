package com.example.cambium.Exception;

public class ExceptionResponse {
    private String errorCode;
    private String errorMsg;

    public ExceptionResponse() {
        super();
    }

    public ExceptionResponse(String errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
