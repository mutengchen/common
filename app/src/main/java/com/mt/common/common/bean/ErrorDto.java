package com.mt.common.common.bean;

/*
 * Created by cmt on 2019/5/22
 */
public class ErrorDto {

    /**
     * errorCode : null
     * message : Masked Patron
     */

    private Object errorCode;
    private String message;

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
