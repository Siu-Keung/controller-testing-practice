package com.oocl.springBootApiPractice2.exception.exceptionModel;

/**
 * @author Dylan Wei
 * @date 2018-07-28 20:39
 */
public class DuplicateResourceIDException extends RuntimeException{
    private static final String message = "ID不允许重复";

    public DuplicateResourceIDException(){
        super(message);
    }

}
