package com.chenjianxiong.cloud.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author :
 * @date :
 * @description :
 */
@Embeddable
public class UserFilePrimaryKey  implements Serializable {
    @Column(name = "file_id")
    private int fileId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "folder_id")
    private int folderId;

    public UserFilePrimaryKey() {

    }

    public UserFilePrimaryKey(int fileId, int userId, int folderId) {
        this.fileId = fileId;
        this.userId = userId;
        this.folderId = folderId;
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

    @Override
    public String toString() {
        return "UserFilePrimaryKey{" +
                "fileId=" + fileId +
                ", userId=" + userId +
                ", folderId=" + folderId +
                '}';
    }
}
