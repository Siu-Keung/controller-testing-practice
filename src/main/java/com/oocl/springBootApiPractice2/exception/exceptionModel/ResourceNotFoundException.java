package com.oocl.springBootApiPractice2.exception.exceptionModel;

/**
 * @author Dylan Wei
 * @date 2018-07-28 15:49
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final String message = "你所请求的资源不存在";

    public ResourceNotFoundException(){
        super(message);
    }
}
