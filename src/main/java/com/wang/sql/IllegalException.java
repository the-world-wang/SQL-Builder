package com.wang.sql;

/**
 * Created by paopao on 16/11/26.
 */
public class IllegalException extends RuntimeException {

    public IllegalException(String message) {
        super(message);
    }

    public IllegalException(String message, Throwable cause) {
        super(message, cause);
    }
}
