package com.liuscoding.api.exception;

import lombok.Getter;

/**
 * @program: java-learning->APIException
 * @description: 自定义异常
 * @author: liushuai
 * @create: 2020-04-24 11:05
 **/
@Getter
public class APIException extends RuntimeException {
    private int code;
    private String msg;

    public APIException(){
        this(1001,"接口错误");
    }

    public APIException(String msg){
        this(1001,msg);
    }

    public APIException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
