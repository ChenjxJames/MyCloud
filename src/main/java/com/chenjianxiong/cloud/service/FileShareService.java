package com.chenjianxiong.cloud.service;

import com.chenjianxiong.cloud.model.FileShare;
import com.chenjianxiong.cloud.model.User;
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

    public List<UserFileVo> getShareToMe(int userId);
}
