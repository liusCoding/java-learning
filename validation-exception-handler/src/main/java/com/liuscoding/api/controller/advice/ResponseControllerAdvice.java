package com.liuscoding.api.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuscoding.api.exception.APIException;
import com.liuscoding.api.vo.ResultVo;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @program: java-learning->ResponseControllerAdvice
 * @description:
 * @author: liushuai
 * @create: 2020-04-24 15:11
 **/
@RestControllerAdvice(basePackages = "com.liuscoding.api.controller")
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        //如果接口返回的类型的本身就是ResultVO那就没有必要进行额外的操作，返回false
        return ! returnType.getGenericParameterType().equals(ResultVo.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        //String 类型不能直接包装，所以要进行特别的处理
        if(returnType.getGenericParameterType().equals(String.class)){
            ObjectMapper  objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(new ResultVo<>(data));
            } catch (JsonProcessingException e) {
                throw new APIException("返回String类型错误");
            }
        }
        //将原本的数据包装在ResultVo里面
        return new ResultVo<>(data);
    }
}
