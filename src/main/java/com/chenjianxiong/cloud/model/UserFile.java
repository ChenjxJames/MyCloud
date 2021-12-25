package com.chenjianxiong.cloud.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author :
 * @date :
 * @description : 用户文件关联模型类
 */
@Entity
@DynamicUpdate
@Table(name = "user_file")
public class UserFile  implements Serializable {
    @EmbeddedId
    private UserFilePrimaryKey id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "create_time")
    private Date createTime;

    public UserFile() {

    }

    public UserFile(UserFilePrimaryKey id, String fileName, Date createTime) {
        this.id = id;
        this.fileName = fileName;
        this.createTime = createTime;
    }

    public UserFilePrimaryKey getId() {
        return id;
    }

    public void setId(UserFilePrimaryKey id) {
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
        return "UserFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
