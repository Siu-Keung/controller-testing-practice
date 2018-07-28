package com.oocl.springBootApiPractice2.exception.handler;

import com.oocl.springBootApiPractice2.exception.exceptionModel.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dylan Wei
 * @date 2018-07-28 15:46
 */
@ControllerAdvice
@ResponseBody
public class ExceptionInterceptor {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public String handleResrouceNotFoundException(ResourceNotFoundException e){
        return e.getMessage();
    }

}