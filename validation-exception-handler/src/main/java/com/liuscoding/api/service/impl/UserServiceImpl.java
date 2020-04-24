package com.liuscoding.api.service.impl;

import com.liuscoding.api.entity.User;
import com.liuscoding.api.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @program: java-learning->UserServiceImpl
 * @description:
 * @author: liushuai
 * @create: 2020-04-23 10:46
 **/
@Service
public class UserServiceImpl implements IUserService {
    /**
     * @param user 用户对象
     * @return 成功则返回“success”,失败则返回失败信息
     */
    @Override
    public String addUser(User user) {
        //直接返回业务逻辑
        return "success";
    }
}
