package com.oocl.springBootApiPractice2.exception;

/**
 * @author Dylan Wei
 * @date 2018-07-28 22:21
 */
public class IllegalCommandException extends RuntimeException{
    private static final String message = "非法指令";

    public IllegalCommandException(){
        super(message);
    }
}
