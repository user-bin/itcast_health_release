package com.itheima.exception;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class BusinessRuntimeException extends RuntimeException {
    public BusinessRuntimeException(String message){
        super(message);
    }
}

