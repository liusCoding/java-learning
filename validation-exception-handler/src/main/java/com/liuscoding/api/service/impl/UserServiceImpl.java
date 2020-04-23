package com.liuscoding.api.service.impl;

import com.liuscoding.api.entity.User;
import com.liuscoding.api.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

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
        //参数校验
        if(Objects.isNull(user) || Objects.isNull(user.getId()) || Objects.isNull(user.getAccount())
                || Objects.isNull(user.getPassword())|| Objects.isNull(user.getEmail())
        ){
            return "对象或者对象字段不能为空";
        }

        if (StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getEmail())) {
            return "不能输入空字符串";
        }
        if (user.getAccount().length() < 6 || user.getAccount().length() > 11) {
            return "账号长度必须是6-11个字符";
        }
        if (user.getPassword().length() < 6 || user.getPassword().length() > 16) {
            return "账号长度必须是6-16个字符";
        }
        if (!Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", user.getEmail())) {
            return "邮箱格式不正确";
        }
        //直接返回业务逻辑
        return "success";
    }
}
