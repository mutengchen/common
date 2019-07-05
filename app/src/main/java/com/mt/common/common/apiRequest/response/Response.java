package com.mt.common.common.apiRequest.response;

/**
 * @author Wellington Costa on 22/12/2017.
 */
public class Response<T> {

    public boolean isSuccess() {
        return infoCode == 1;
    }
    public boolean isTokenExpired() {
        return infoCode == -1;
    }

    public int infoCode;
    public String message;
    public int size;
    public T data;
}
