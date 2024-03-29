package com.chenjianxiong.cloud.service;

import com.chenjianxiong.cloud.model.User;

/**
 * @author :
 * @date :
 * @description :
 */
public interface UserService {

    public User register(User user);

    public User login(String username, String password);

    public User changeInfo(User user);

    public User findByUsername(String username);

    public User findByEmail(String email);
}
