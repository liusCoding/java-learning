package com.liuscoding.api.controller;

import com.liuscoding.api.entity.User;
import com.liuscoding.api.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @program: java-learning->UserController
 * @description:
 * @author: liushuai
 * @create: 2020-04-23 10:41
 **/

@RestController
@Api(tags = {"用户接口"})
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public String addUser(@Valid  @RequestBody User user, BindingResult bindingResult){
        //如果有参数校验失败，会将错误信息封装成对象在BingResult里
        for (ObjectError error : bindingResult.getAllErrors()) {
            return error.getDefaultMessage();
        }
        return userService.addUser(user);
    }

}
