package com.chenjianxiong.cloud.dao;

import com.chenjianxiong.cloud.model.FileShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author ：
 * @date ：Created in 2020/6/15 17:53
 * @description：
 */
public interface FileShareRepository extends JpaRepository<FileShare, Integer> {

    @Query(value = "SELECT fs FROM FileShare fs WHERE file_id=:fileId AND share_user=:shareUser AND create_user=:createUser")
    public FileShare find(@Param("fileId") int fileId, @Param("shareUser") int shareUser, @Param("createUser") int createUser);

    @Query(value = "SELECT fs FROM FileShare fs WHERE share_user=:shareUser")
    public List<FileShare> findByShareUser(@Param("shareUser") int shareUser);

    @Query(value = "SELECT fs FROM FileShare fs WHERE create_user=:createUser")
    public List<FileShare> findByCreateUser(@Param("createUser") int createUser);

    public FileShare save(FileShare fileShare);
}
