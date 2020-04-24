package com.liuscoding.api.controller;

import com.liuscoding.api.entity.User;
import com.liuscoding.api.service.IUserService;
import com.liuscoding.api.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String addUser(@Valid  @RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/getUser")
    public ResultVo<User> getUser(){
        User user = new User();
        user.setAccount("133333333");
        user.setId(33L);
        user.setPassword("333333333");
        user.setEmail("333@qq.com");

        return new ResultVo<>(user);
    }

}
