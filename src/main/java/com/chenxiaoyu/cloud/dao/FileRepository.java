package com.chenxiaoyu.cloud.dao;

import com.chenxiaoyu.cloud.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：
 * @date ：Created in 2020/6/15 15:48
 * @description：使用hibernate
 */
public interface FileRepository  extends JpaRepository<File, Integer> {

    @Query(value = "SELECT f FROM File f WHERE fileId in (:fileIdList)")
    public List<File> findByFileIdList(@Param("fileIdList") List<Integer> fileIdList);

    public File findByFileId(int fileId);

    public File save(File file);

    @Query(value = "SELECT * FROM file WHERE file_id NOT IN (SELECT file_id FROM user_file UNION SELECT folder_id as file_id FROM user_file UNION SELECT 0 AS file_id)", nativeQuery = true)
    public List<File> findByUseless();

    @Modifying
    @Transactional
    @Query(value = "delete from File where file_id in (:fileIdList)", nativeQuery = true)
    public int delete(@Param("fileIdList") List<Integer> fileIdList);

    @Query(value = "SELECT f FROM File f WHERE md5=:md5 AND size=:size")
    public File findByMd5AndSize(@Param("md5") String md5, @Param("size") long size);
}
