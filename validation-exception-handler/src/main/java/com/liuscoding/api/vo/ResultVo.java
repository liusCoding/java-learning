package com.liuscoding.api.vo;

import com.liuscoding.api.enums.ResultCode;
import lombok.Data;

/**
 * @program: java-learning->ResultVo
 * @description:
 * @author: liushuai
 * @create: 2020-04-24 11:29
 **/

@Data
public class ResultVo<T> {
    /**
     * 状态码 比如1000代表响应成功
     */
    private int code;

    /**
     * 响应信息，用来说明响应情况
     */
    private String msg;

    /**
     * 响应的具体数据
     */
    private T data;

    public ResultVo(T data){
        this (ResultCode.SUCCESS,data);
    }

    public ResultVo(ResultCode resultCode,T data){
        this.code = resultCode.getCode() ;
        this.msg = resultCode.getMsg();
        this.data = data;
    }

}
