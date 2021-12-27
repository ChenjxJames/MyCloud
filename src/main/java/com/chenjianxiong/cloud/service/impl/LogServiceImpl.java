package com.chenjianxiong.cloud.service.impl;

import com.chenjianxiong.cloud.dao.FileShareRepository;
import com.chenjianxiong.cloud.dao.UserFileRepository;
import com.chenjianxiong.cloud.dao.UserRepository;
import com.chenjianxiong.cloud.model.*;
import com.chenjianxiong.cloud.service.FileService;
import com.chenjianxiong.cloud.service.FileShareService;
import com.chenjianxiong.cloud.vo.FileShareVo;
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
    private UserRepository userRepository;

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
        List<FileShare> fileShareList = new ArrayList<>();
        for(FileShare newFileShare: this.fileShareRepository.findByCreateUser(userId)) {
            FileSharePrimaryKey newFileShareId = newFileShare.getId();
            boolean isExisted = false;
            for(FileShare fileShare: fileShareList) {
                FileSharePrimaryKey fileShareId = fileShare.getId();
                if (newFileShareId.getCreateUser() == fileShareId.getCreateUser() && newFileShareId.getFileId() == fileShareId.getFileId() && newFileShareId.getFolderId() == fileShareId.getFolderId()) {
                    isExisted = true;
                    break;
                }
            }
            if (!isExisted) {
                fileShareList.add(newFileShare);
            }
        }
        return this.getByShareList(fileShareList);
    }

    @Override
    public List<FileShareVo> getMyShareByUserFile(UserFilePrimaryKey userFilePrimaryKey) {
        List<FileShare> fileShareList = this.fileShareRepository.findByUserFile(userFilePrimaryKey.getFolderId(), userFilePrimaryKey.getFileId(), userFilePrimaryKey.getUserId());
        List<FileShareVo> fileShareVoList = new ArrayList<>();
        for(FileShare fileShare: fileShareList) {
            User user = this.userRepository.findById(fileShare.getId().getShareUser());
            fileShareVoList.add(new FileShareVo(fileShare, user.getUsername()));
        }
        return fileShareVoList;
    }

    @Override
    public List<UserFileVo> getShareToMe(int userId) {
        List<FileShare> fileShareList = this.fileShareRepository.findByShareUser(userId);
        return this.getByShareList(fileShareList);
    }

    @Override
    public List<Integer> getFileId(int folderId) {
        return this.userFileRepository.findFileIdByFolderId(folderId);
    }

    @Override
    public boolean delete(List<FileSharePrimaryKey> fileSharePrimaryKeyList) {
        int count = 0;
        for(FileSharePrimaryKey fileSharePrimaryKey: fileSharePrimaryKeyList) {
            count += this.fileShareRepository.delete(fileSharePrimaryKey.getFolderId(), fileSharePrimaryKey.getFileId(), fileSharePrimaryKey.getShareUser(), fileSharePrimaryKey.getCreateUser());
        }
        return count == fileSharePrimaryKeyList.size();
    }

    @Override
    public UserFile rename(UserFile userFile) {
        UserFile uf = this.userFileRepository.findByFileId(userFile.getId().getFileId(), userFile.getId().getFolderId());
        uf.setFileName(userFile.getFileName());
        return this.userFileRepository.save(uf);
    }
}
