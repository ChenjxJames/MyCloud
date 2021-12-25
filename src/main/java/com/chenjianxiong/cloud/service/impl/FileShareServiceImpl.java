package com.chenjianxiong.cloud.service.impl;

import com.chenjianxiong.cloud.dao.FileRepository;
import com.chenjianxiong.cloud.dao.FileShareRepository;
import com.chenjianxiong.cloud.dao.UserFileRepository;
import com.chenjianxiong.cloud.model.*;
import com.chenjianxiong.cloud.service.FileService;
import com.chenjianxiong.cloud.service.FileShareService;
import com.chenjianxiong.cloud.vo.UserFileVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileShareServiceImpl implements FileShareService {

    @Resource
    private FileShareRepository fileShareRepository;

    @Resource
    private FileRepository fileRepository;

    @Resource
    private UserFileRepository userFileRepository;

    @Resource
    private FileService fileService;

    @Override
    public FileShare share(int folderId, int fileId, User user, User shareUser,int shareUserRole) {
        FileSharePrimaryKey fileSharePrimaryKey = new FileSharePrimaryKey(folderId, fileId, shareUser.getId(), user.getId());
        FileShare fileShare = new FileShare(fileSharePrimaryKey, shareUserRole, new Date());
        return this.fileShareRepository.save(fileShare);
    }

    @Override
    public List<UserFileVo> getByShareList(List<FileShare> fileShareList) {
        List<UserFile> userFileList = new ArrayList<>();
        for (FileShare fileShare : fileShareList) {
            FileSharePrimaryKey fileSharePrimaryKey = fileShare.getId();
            userFileList.add(this.userFileRepository.findByFileId(fileSharePrimaryKey.getCreateUser(), fileSharePrimaryKey.getFileId(), fileSharePrimaryKey.getFolderId()));
        }
        return this.fileService.getFileInfo(userFileList);
    }

    @Override
    public List<UserFileVo> getMyShare(int userId) {
        List<FileShare> fileShareList = this.fileShareRepository.findByCreateUser(userId);
        return this.getByShareList(fileShareList);
    }

    @Override
    public List<UserFileVo> getShareToMe(int userId) {
        List<FileShare> fileShareList = this.fileShareRepository.findByShareUser(userId);
        return this.getByShareList(fileShareList);
    }
}
