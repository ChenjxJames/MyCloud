package com.chenjianxiong.cloud.dao;

import com.chenjianxiong.cloud.model.UserFile;
import com.chenjianxiong.cloud.model.UserFilePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author :
 * @date :
 * @description :
 */
public interface UserFileRepository extends JpaRepository<UserFile, Integer> {

    @Query(value = "SELECT file_id FROM user_file WHERE folder_id=:folderId", nativeQuery = true)
    public List<Integer> findFileIdByFolderId(@Param("folderId") int folderId);

    @Query(value = "SELECT file_id FROM user_file WHERE user_id=:userId AND folder_id=:folderId", nativeQuery = true)
    public List<Integer> findFileIdByUserIdAndFolderId(@Param("userId") int userId, @Param("folderId") int folderId);

    @Query(value = "SELECT file_id FROM user_file WHERE user_id=:userId AND (folder_id in (:folderIdList))", nativeQuery = true)
    public List<Integer> findFileIdByUserIdAndFolderIdList(@Param("userId") int userId, @Param("folderIdList") List<Integer> folderIdList);

    @Query(value = "SELECT uf FROM UserFile uf WHERE folder_id=:folderId AND (file_id in (:fileIdList))")
    public List<UserFile> findByFileIdList(@Param("folderId") int folderId, @Param("fileIdList") List<Integer> fileIdList);

    @Query(value = "SELECT uf FROM UserFile uf WHERE user_id=:userId AND file_id=:fileId AND folder_id=:folderId")
    public UserFile findByFileId(@Param("userId") int userId, @Param("fileId") int fileId, @Param("folderId") int folderId);

    @Query(value = "SELECT uf FROM UserFile uf WHERE file_id=:fileId AND folder_id=:folderId")
    public UserFile findByFileId(@Param("fileId") int fileId, @Param("folderId") int folderId);

    @Query(value = "SELECT uf FROM UserFile uf WHERE user_id=:userId AND folder_id=:folderId AND file_name=:fileName")
    public UserFile findByFileName(@Param("userId") int userId, @Param("folderId") int folderId, @Param("fileName") String fileName);

    @Query(value = "SELECT uf FROM UserFile uf WHERE user_id=:userId AND folder_id=:folderId")
    public List<UserFile> findByUserIdAndFolderId(@Param("userId") int userId, @Param("folderId") int folderId);

    @Modifying
    @Transactional
    @Query(value = "delete from UserFile u where user_id=:userId AND(folder_id in (:folderIdList))")
    public int deleteByUserIdAndFolderId(@Param("userId") int userId, @Param("folderIdList") List<Integer> folderIdList);

    @Modifying
    @Transactional
    @Query(value = "delete from UserFile u where u.id in (:idList)")
    public int deleteById(@Param("idList") List<UserFilePrimaryKey> idList);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_file SET folder_id=:newFolderId WHERE (file_id in (:fileIdList)) AND user_id=:userId AND folder_id=:folderId", nativeQuery = true)
    public int updateFolder(@Param("fileIdList") List<Integer> fileIdList, @Param("userId") int userId, @Param("folderId") int folderId, @Param("newFolderId") int newFolderId);

    public UserFile save(UserFile userFile);

    public UserFile findById(UserFilePrimaryKey id);
}
