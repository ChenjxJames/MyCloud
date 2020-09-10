package com.chenjianxiong.cloud.service.impl;

import com.chenjianxiong.cloud.dao.FileRepository;
import com.chenjianxiong.cloud.dao.FileShareRepository;
import com.chenjianxiong.cloud.dao.UserFileRepository;
import com.chenjianxiong.cloud.model.File;
import com.chenjianxiong.cloud.model.UserFile;
import com.chenjianxiong.cloud.model.UserFilePrimaryKey;
import com.chenjianxiong.cloud.service.FileService;
import com.chenjianxiong.cloud.utils.FileUtils;
import com.chenjianxiong.cloud.vo.Result;
import com.chenjianxiong.cloud.vo.UserFileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：
 * @date ：Created in 2020/6/15 19:07
 * @description：
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserFileRepository userFileRepository;

    @Autowired
    private FileShareRepository fileShareRepository;

    @Override
    public UserFileVo createFolder(int userId, int folderId, String fileName) {
        File file = new File(0, "folder", "0", "", 0);
        file = this.fileRepository.save(file);
        UserFile userFile = new UserFile(new UserFilePrimaryKey(file.getFileId(), userId, folderId), fileName, new Date());
        userFile = this.userFileRepository.save(userFile);
        return new UserFileVo(userFile, file);
    }

    @Override
    public Result upload(String fileName, int folderId, int userId, MultipartFile multipartFile) throws Exception {
        Result result = new Result(0, "文件上传成功。");
        String md5 = FileUtils.getMd5(multipartFile);
        long size = multipartFile.getSize();
        File file = this.fileRepository.findByMd5AndSize(md5, size);
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        if ("".equals(fileName)) {
            fileName = multipartFile.getOriginalFilename();
        } else {
            fileName = fileName + "." + suffix;
        }
        if (this.userFileRepository.findByFileName(userId, folderId, fileName) != null) {
            result.setState(-36);
            result.setInfo("文件上传失败，有同名文件存在。");
        } else {
            if (file == null) {
                String path = FileUtils.upload(multipartFile, md5 + Long.toString(size) + "." + suffix);
                file = new File("file", md5, path, size);
                file = this.fileRepository.save(file);
            }
            UserFile userFile = new UserFile(new UserFilePrimaryKey(file.getFileId(), userId, folderId), fileName, new Date());
            userFile = this.userFileRepository.save(userFile);
            result.setData(new UserFileVo(userFile, file));
        }
        return result;
    }

    @Override
    public UserFileVo download(int userId, int fileId) {
        UserFile userFile = this.userFileRepository.findByFileId(userId, fileId);
        if (userFile != null) {
            File file = this.fileRepository.findByFileId(fileId);
            return new UserFileVo(userFile, file);
        }
        return null;
    }

    @Override
    public UserFile update(UserFile userFile) {
        UserFile uf = this.userFileRepository.findById(userFile.getId());
        uf.setFileName(userFile.getFileName());
        return this.userFileRepository.save(uf);
    }

    @Override
    public boolean move(List<Integer> fileIdList, int userId, int folderId, int newFolderId) {
        return this.userFileRepository.updateFolder(fileIdList, userId, folderId, newFolderId) == fileIdList.size();
    }

    @Override
    public void deleteFolder(int userId, List<Integer> fileIdList) {
        List<File> fileList = this.fileRepository.findByFileIdList(fileIdList);
        List<Integer> folderIdList = new ArrayList<>();
        for (File file : fileList) {
            if (file.getFileType().equals("folder")) {
                folderIdList.add(file.getFileId());
            }
        }
        if (folderIdList.size() > 0) {
            fileIdList = this.userFileRepository.findFileIdByUserIdAndFolderIdList(userId, folderIdList);
            this.userFileRepository.deleteByUserIdAndFolderId(userId, folderIdList);
            deleteFolder(userId, fileIdList);
        }
    }


    @Override
    public void deleteFile() {
        List<File> fileList = this.fileRepository.findByUseless();
        List<Integer> fileIdList = new ArrayList<>();
        for (File file : fileList) {
            fileIdList.add(file.getFileId());
            FileUtils.delete(file.getPath());
        }
        this.fileRepository.delete(fileIdList);
    }

    @Override
    public boolean delete(List<UserFilePrimaryKey> userFileIdList) {
        try {
            this.userFileRepository.deleteById(userFileIdList);
            List<Integer> fileIdList = new ArrayList<>();
            for (UserFilePrimaryKey userFileId : userFileIdList) {
                fileIdList.add(userFileId.getFileId());
            }
            this.deleteFolder(userFileIdList.get(0).getUserId(), fileIdList);
            this.deleteFile();
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public List<Integer> getFileId(int userId, int folderId) {
        return this.userFileRepository.findFileIdByUserIdAndFolderId(userId, folderId);
    }

    @Override
    public List<UserFileVo> getFileInfo(List<UserFile> userFileList) {
        List<UserFileVo> userFileVoList = new ArrayList<>();
        List<Integer> fileIdList = new ArrayList<>();
        for (UserFile userFile : userFileList) {
            fileIdList.add(userFile.getId().getFileId());
        }
        List<File> fileList = this.fileRepository.findByFileIdList(fileIdList);
        for (int i = 0; i < userFileList.size(); i++) {
            UserFileVo userFileVo = new UserFileVo(userFileList.get(i), fileList.get(i));
            userFileVoList.add(userFileVo);
        }
        return userFileVoList;
    }

    @Override
    public List<UserFileVo> getFileInfo(int userId, int folderId, List<Integer> fileIdList) {
        List<UserFile> userFileList = this.userFileRepository.findByFileIdList(userId, folderId, fileIdList);
        return this.getFileInfo(userFileList);
    }

    @Override
    public List<UserFileVo> getFileInfo(int userId, int folderId) {
        List<UserFile> userFileList = this.userFileRepository.findByUserIdAndFolderId(userId, folderId);
        return this.getFileInfo(userFileList);
    }
}
