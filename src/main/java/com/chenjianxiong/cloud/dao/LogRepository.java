package com.chenjianxiong.cloud.dao;

import com.chenjianxiong.cloud.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author :
 * @date :
 * @description : 日志
 */
public interface LogRepository extends JpaRepository<Log, Integer> {

    @Query(value = "SELECT lg FROM Log lg WHERE folder_id=:folderId AND file_id=:fileId AND file_creator_user=:fileCreatorUser")
    public List<Log> findByFile(@Param("folderId") int folderId, @Param("fileId") int fileId, @Param("fileCreatorUser") int fileCreatorUser);

    public Log save(Log log);
}
