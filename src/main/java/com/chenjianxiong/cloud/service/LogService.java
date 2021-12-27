package com.chenjianxiong.cloud.service;

import com.chenjianxiong.cloud.model.*;
import com.chenjianxiong.cloud.vo.FileShareVo;
import com.chenjianxiong.cloud.vo.UserFileVo;

import java.util.List;

/**
 * @author :
 * @date :
 * @description : 文件分享服务接口
 */
public interface FileShareService {

    public FileShare share(int folderId, int fileId, User user, User shareUser, int shareUserRole);

    public List<UserFileVo> getByShareList(List<FileShare> fileShareList);

    public List<UserFileVo> getMyShare(int userId);

    public List<FileShareVo> getMyShareByUserFile(UserFilePrimaryKey userFilePrimaryKey);

    public List<UserFileVo> getShareToMe(int userId);

    public List<Integer> getFileId(int folderId);

    public boolean delete(List<FileSharePrimaryKey> fileSharePrimaryKeyList);

    public UserFile rename(UserFile userFile);
}
