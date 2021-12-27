package com.chenjianxiong.cloud.dao;

import com.chenjianxiong.cloud.model.FileShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author :
 * @date :
 * @description : 分享
 */
public interface FileShareRepository extends JpaRepository<FileShare, Integer> {

    @Query(value = "SELECT fs FROM FileShare fs WHERE folder_id=:folderId AND file_id=:fileId AND share_user=:shareUser")
    public FileShare findByShareFile(@Param("folderId") int folderId, @Param("fileId") int fileId, @Param("shareUser") int shareUser);

    @Query(value = "SELECT fs FROM FileShare fs WHERE folder_id=:folderId AND file_id=:fileId AND create_user=:createUser")
    public List<FileShare> findByUserFile(@Param("folderId") int folderId, @Param("fileId") int fileId, @Param("createUser") int createUser);

    @Query(value = "SELECT fs FROM FileShare fs WHERE share_user=:shareUser")
    public List<FileShare> findByShareUser(@Param("shareUser") int shareUser);

    @Query(value = "SELECT fs FROM FileShare fs WHERE create_user=:createUser")
    public List<FileShare> findByCreateUser(@Param("createUser") int createUser);

    @Modifying
    @Transactional
    @Query(value = "delete from FileShare where folder_id=:folderId AND file_id=:fileId AND share_user=:shareUser AND create_user=:createUser")
    public int delete(@Param("folderId") int folderId, @Param("fileId") int fileId, @Param("shareUser") int shareUser, @Param("createUser") int createUser);

    public FileShare save(FileShare fileShare);
}
