package com.chenjianxiong.cloud.service.impl;

import com.chenjianxiong.cloud.dao.UserRepository;
import com.chenjianxiong.cloud.model.User;
import com.chenjianxiong.cloud.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author :
 * @date :
 * @description :
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRpy;

    public User register(User user) {
        return this.userRpy.save(user);
    }

    public User login(String username, String password) {
        User user = this.userRpy.findByUsername(username);
        if (user != null && user.getPassword().equalsIgnoreCase(password)) {
            return user;
        }
        return new User();
    }

    public User changeInfo(User user) {
        User u = this.userRpy.findById(user.getId());
        if (u.getId() != 0) {
            return this.userRpy.save(user);
        }
        return new User();
    }

    public User findByUsername(String username) {
        return this.userRpy.findByUsername(username);
    }

    public User findByEmail(String email){
        return this.userRpy.findByEmail(email);
    }
}
