package com.liuscoding.api.handler;

import com.liuscoding.api.enums.ResultCode;
import com.liuscoding.api.exception.APIException;
import com.liuscoding.api.vo.ResultVo;
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
    public ResultVo<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        //从异常对象中拿到ObjectError对象
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        //注意哦，这里传递的响应码枚举
        return new ResultVo<>(ResultCode.VALIDATE_FAILED,error.getDefaultMessage());
    }

    @ExceptionHandler(APIException.class)
    public ResultVo<String> APIExceptionHandler(APIException e){
        //注意哦，这里传递的响应码枚举
        return new ResultVo<>(ResultCode.FAILED,e.getMsg());
    }

}
