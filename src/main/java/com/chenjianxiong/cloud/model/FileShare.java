package com.chenjianxiong.cloud.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author :
 * @date :
 * @description : 文件分享模型类
 */
@Entity
@DynamicUpdate
@Table(name = "file_share")
public class FileShare implements Serializable {
    @EmbeddedId
    private FileSharePrimaryKey id;

    @Column(name = "share_user_role")
    private int shareUserRole;

    @Column(name = "create_time")
    private Date createTime;

    public FileShare() {

    }

    public FileShare(FileSharePrimaryKey id, int shareUserRole, Date createTime) {
        this.id = id;
        this.shareUserRole = shareUserRole;
        this.createTime = createTime;
    }

    public FileSharePrimaryKey getId() {
        return id;
    }

    public void setId(FileSharePrimaryKey id) {
        this.id = id;
    }

    public int getShareUserRole() {
        return shareUserRole;
    }

    public void setShareUserRole(int shareUserRole) {
        this.shareUserRole = shareUserRole;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FileShare{" +
                "id=" + id +
                ", shareUserRole='" + shareUserRole + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
