package com.chenjianxiong.cloud.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author :
 * @date :
 * @description : 日志模型类
 */
@Entity
@DynamicUpdate
@Table(name = "log")
public class Log implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private int id;

    @Column(name = "folder_id")
    private int folderId;
    @Column(name = "file_id")
    private int fileId;
    @Column(name = "share_user")
    private int shareUser;
    @Column(name = "file_creator_user")
    private int fileCreatorUser;

    @Column(name = "log_content")
    private String logContent;

    @Column(name = "create_time")
    private Date createTime;

    public Log() {

    }

    public Log(int folderId, int fileId, int shareUser, int fileCreatorUser, String logContent) {
        this.folderId = folderId;
        this.fileId = fileId;
        this.shareUser = shareUser;
        this.fileCreatorUser = fileCreatorUser;
        this.logContent = logContent;
        this.createTime = new Date();
    }

    public Log(int id, int folderId, int fileId, int shareUser, int fileCreatorUser, String logContent, Date createTime) {
        this.id = id;
        this.folderId = folderId;
        this.fileId = fileId;
        this.shareUser = shareUser;
        this.fileCreatorUser = fileCreatorUser;
        this.logContent = logContent;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getFileCreatorUser() {
        return fileCreatorUser;
    }

    public void setFileCreatorUser(int fileCreatorUser) {
        this.fileCreatorUser = fileCreatorUser;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", folderId=" + folderId +
                ", fileId=" + fileId +
                ", shareUser=" + shareUser +
                ", fileCreatorUser=" + fileCreatorUser +
                ", logContent='" + logContent + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
