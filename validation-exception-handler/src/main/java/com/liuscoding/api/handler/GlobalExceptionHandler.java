package com.liuscoding.api.handler;

import com.liuscoding.api.exception.APIException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: java-learning->GlobalExceptionHandler
 * @description:
 * @author: liushuai
 * @create: 2020-04-24 10:31
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        //从异常对象中拿到ObjectError对象
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        //然后提取错误提示信息进行返回
        return error.getDefaultMessage();
    }

    @ExceptionHandler(APIException.class)
    public String APIExceptionHandler(APIException e){
        return e.getMsg();
    }

}
