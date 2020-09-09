package com.chenxiaoyu.cloud.service;

import com.chenxiaoyu.cloud.model.File;
import com.chenxiaoyu.cloud.model.UserFile;
import com.chenxiaoyu.cloud.model.UserFilePrimaryKey;
import com.chenxiaoyu.cloud.vo.Result;
import com.chenxiaoyu.cloud.vo.UserFileVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ：
 * @date ：Created in 2020/6/15 17:43
 * @description：文件服务接口
 */
public interface FileService {

    public UserFileVo createFolder(int userId, int folderId, String fileName);

    public Result upload(String fileName, int folderId, int userId, MultipartFile file) throws Exception;

    public UserFileVo download(int userId, int fileId);

    public UserFile update(UserFile userFile);

    public boolean move(List<Integer> fileIdList, int userId, int folderId, int newFolderId);

    // 递归删除用户文件夹
    public void deleteFolder(int userId, List<Integer> fileIdList);

    // 删除没有用户引用的文件
    public void deleteFile();

    public boolean delete(List<UserFilePrimaryKey> userFileIdList);

    public List<Integer> getFileId(int userId, int folderId);

    public List<UserFileVo> getFileInfo(List<UserFile> userFileList);

    public List<UserFileVo> getFileInfo(int userId, int folderId, List<Integer> fileIdList);

    public List<UserFileVo> getFileInfo(int userId, int folderId);
}
