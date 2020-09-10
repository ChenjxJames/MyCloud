package com.chenjianxiong.cloud.service;

import com.chenjianxiong.cloud.model.User;

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
