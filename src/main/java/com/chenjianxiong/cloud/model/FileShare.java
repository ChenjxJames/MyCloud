package com.chenjianxiong.cloud.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ：
 * @date ：Created in 2020/6/15 15:43
 * @description：文件分享模型类
 */
@Entity
@DynamicUpdate
@Table(name = "file_share")
public class FileShare implements Serializable {
    @EmbeddedId
    private FileSharePrimaryKey id;

    @Column(name = "file_name")
    private String fileName;
    @Column(name = "create_time")
    private Date createTime;

    public FileShare() {

    }

    public FileShare(FileSharePrimaryKey id, String fileName, Date createTime) {
        this.id = id;
        this.fileName = fileName;
        this.createTime = createTime;
    }

    public FileSharePrimaryKey getId() {
        return id;
    }

    public void setId(FileSharePrimaryKey id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "FileShare{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
