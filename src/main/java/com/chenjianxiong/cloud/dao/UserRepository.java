package com.chenjianxiong.cloud.dao;

import com.chenjianxiong.cloud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * @author ：
 * @date ：Created in 2020/6/14 1:11
 * @description：
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findById(int id);

    public User save(User user);

    @Query(value = "SELECT u FROM User u WHERE user_name=:username")
    public User findByUsername(@Param("username") String username);

    @Query(value = "SELECT u FROM User u WHERE user_email=:email")
    public User findByEmail(@Param("email") String email);
}
