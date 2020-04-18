package com.natsuki_kining.ssr.exception;

/**
 *  异常
 *
 * @Author natsuki_kining
 * @Date 2020-4-12 23:03:53
 * @Version 1.0.0
 **/
public class SSRException extends RuntimeException{

    public SSRException(String message){
        super(message);
    }

    public SSRException(String message,Throwable cause){
        super(message,cause);
    }
}
