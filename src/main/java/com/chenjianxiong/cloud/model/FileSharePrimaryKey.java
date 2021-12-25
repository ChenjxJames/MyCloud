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
public class FileSharePrimaryKey implements Serializable {
    @Column(name = "folder_id")
    private int folderId;
    @Column(name = "file_id")
    private int fileId;
    @Column(name = "share_user")
    private int shareUser;
    @Column(name = "create_user")
    private int createUser;

    public FileSharePrimaryKey() {

    }

    public FileSharePrimaryKey(int folderId, int fileId, int shareUser, int createUser) {
        this.folderId = folderId;
        this.fileId = fileId;
        this.shareUser = shareUser;
        this.createUser = createUser;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getShareUser() {
        return shareUser;
    }

    public void setShareUser(int shareUser) {
        this.shareUser = shareUser;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "FileSharePrimaryKey{" +
                "folderId=" + folderId +
                "fileId=" + fileId +
                ", shareUser=" + shareUser +
                ", createUser=" + createUser +
                '}';
    }
}
