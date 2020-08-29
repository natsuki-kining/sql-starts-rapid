package com.natsuki_kining.ssr.core.exception;

/**
 * 没有找到code异常类
 *
 * @Author : natsuki_kining
 * @Date : 2020/7/18 16:00
 */
public class CodeNotFoundException extends RuntimeException {

    public CodeNotFoundException(String message) {
        super(message);
    }

    public CodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
