package com.chenjianxiong.cloud.vo;

import com.chenjianxiong.cloud.model.FileShare;
import com.chenjianxiong.cloud.model.FileSharePrimaryKey;

import java.util.Date;

public class FileShareVo extends FileSharePrimaryKey {
    private String shareUsername;

    private int shareUserRole;

    private String shareUserRoleStr;

    private Date createTime;

    public int getShareUserRole() {
        return shareUserRole;
    }

    public void setShareUserRole(int shareUserRole) {
        this.shareUserRole = shareUserRole;
    }

    public FileShareVo(FileShare fileShare, String shareUsername) {
        super(fileShare.getId().getFolderId(), fileShare.getId().getFileId(), fileShare.getId().getShareUser(), fileShare.getId().getCreateUser());
        this.shareUsername = shareUsername;
        this.shareUserRole = fileShare.getShareUserRole();
        this.setShareUserRoleStr(fileShare.getShareUserRole());
        this.createTime = fileShare.getCreateTime();
    }


    public String getShareUsername() {
        return shareUsername;
    }

    public void setShareUsername(String shareUsername) {
        this.shareUsername = shareUsername;
    }

    public String getShareUserRoleStr() {
        return shareUserRoleStr;
    }

    public void setShareUserRoleStr(int shareUserRole) {
        switch (shareUserRole) {
            case 0: {
                this.shareUserRoleStr = "仅查看";
                break;
            }
            case 1: {
                this.shareUserRoleStr = "编辑者";
                break;
            }
            case 2: {
                this.shareUserRoleStr = "管理员";
                break;
            }
        }
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FileShareVo{" +
                "folderId=" + super.getFolderId() +
                "fileId=" + super.getFileId() +
                "shareUser=" + super.getShareUser() +
                "createUser=" + super.getCreateUser() +
                ",shareUsername='" + shareUsername + '\'' +
                ", shareUserRole=" + shareUserRole +
                ", createTime=" + createTime +
                '}';
    }
}
