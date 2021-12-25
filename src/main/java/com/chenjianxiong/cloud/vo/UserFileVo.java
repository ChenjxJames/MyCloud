package com.chenjianxiong.cloud.vo;

import com.chenjianxiong.cloud.model.File;
import com.chenjianxiong.cloud.model.UserFile;

import java.util.Date;

/**
 * @author :
 * @date :
 * @description :
 */
public class UserFileVo {

    private int fileId;
    private int userId;
    private int folderId;
    private String fileName;
    private Date createTime;

    private String fileType;
    private String md5;
    private String path;
    private long size;

    public UserFileVo() {
    }

    public UserFileVo(UserFile userfile, File file) {
        this.fileId = userfile.getId().getFileId();
        this.userId = userfile.getId().getUserId();
        this.folderId = userfile.getId().getFolderId();
        this.fileName = userfile.getFileName();
        this.createTime = userfile.getCreateTime();

        this.fileType = file.getFileType();
        this.md5 = file.getMd5();
        this.path = file.getPath();
        this.size = file.getSize();
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "UserFileVo{" +
                "fileId=" + fileId +
                ", userId=" + userId +
                ", folderId=" + folderId +
                ", fileName='" + fileName + '\'' +
                ", createTime=" + createTime +
                ", fileType='" + fileType + '\'' +
                ", md5='" + md5 + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                '}';
    }
}
