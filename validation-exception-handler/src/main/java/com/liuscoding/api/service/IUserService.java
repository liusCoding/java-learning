package com.liuscoding.api.service;

import com.liuscoding.api.entity.User;

/**
 * @author liuscoding
 * 用户业务接口
 */
public interface IUserService {

    /**
     *
     * @param user 用户对象
     * @return  成功则返回“success”,失败则返回失败信息
     */
    String addUser(User user);
}
