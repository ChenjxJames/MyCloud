package com.chenjianxiong.cloud.vo;

import com.chenjianxiong.cloud.model.Log;

import java.util.Date;

public class LogVo extends Log {

    private String shareUsername;

    public LogVo() {
    }

    public LogVo(String shareUsername) {
        this.shareUsername = shareUsername;
    }

    public LogVo(Log log, String shareUsername) {
        super(log.getId(), log.getFolderId(), log.getFileId(), log.getShareUser(), log.getFileCreatorUser(), log.getLogContent(), log.getCreateTime());
        this.shareUsername = shareUsername;
    }

    public LogVo(int id, int folderId, int fileId, int shareUser, int fileCreatorUser, String logContent, Date createTime, String shareUsername) {
        super(id, folderId, fileId, shareUser, fileCreatorUser, logContent, createTime);
        this.shareUsername = shareUsername;
    }

    public String getShareUsername() {
        return shareUsername;
    }

    public void setShareUsername(String shareUsername) {
        this.shareUsername = shareUsername;
    }

    @Override
    public String toString() {
        return "LogVo{" +
                "id=" + super.getId() +
                ", folderId=" + super.getFolderId() +
                ", fileId=" + super.getFileId() +
                ", shareUser=" + super.getShareUser() +
                ", fileCreatorUser=" + super.getFileCreatorUser() +
                ", logContent='" + super.getLogContent() + '\'' +
                ", shareUsername='" + shareUsername + '\'' +
                ", createTime=" + super.getCreateTime() +
                '}';
    }
}
