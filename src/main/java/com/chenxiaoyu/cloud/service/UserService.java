package com.chenxiaoyu.cloud.service;

import com.chenxiaoyu.cloud.model.User;

/**
 * @author ：
 * @date ：Created in 2020/6/14 1:17
 * @description：
 */
public interface UserService {

    public User register(User user);

    public User login(String username, String password);

    public User changeInfo(User user);

    public User findByUsername(String username);

    public User findByEmail(String email);
}
