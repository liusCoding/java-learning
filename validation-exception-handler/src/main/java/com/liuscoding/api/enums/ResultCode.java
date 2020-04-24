package com.liuscoding.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: java-learning->ResultCode
 * @description: 响应码枚举
 * @author: liushuai
 * @create: 2020-04-24 14:27
 **/

@Getter
@AllArgsConstructor
public enum  ResultCode {

    SUCCESS(1000,"操作成功"),

    FAILED(1001,"响应失败"),

    VALIDATE_FAILED(1002,"参数校验失败"),

    ERROR(5000,"未知错误"),

    ;
    /**
     * 响应码
     */
    private int code;

    /**
     * 提示消息
     */
    private String msg;
}
