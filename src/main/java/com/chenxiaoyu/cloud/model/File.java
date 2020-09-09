package com.chenxiaoyu.cloud.model;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ：
 * @date ：Created in 2020/6/14 23:22
 * @description：文件模型类
 */
@Entity
@DynamicUpdate
@Table(name = "file")
public class File implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "md5")
    @ColumnTransformer(
            write = "UNHEX(?)",
            read = "HEX(md5)")
    private String md5;
    @Column(name = "path")
    private String path;
    @Column(name = "size")
    private long size;

    public File() {

    }

    public File(String fileType, String md5, String path, long size) {
        this.fileType = fileType;
        this.md5 = md5;
        this.path = path;
        this.size = size;
    }

    public File(int fileId, String fileType, String md5, String path, long size) {
        this.fileId = fileId;
        this.fileType = fileType;
        this.md5 = md5;
        this.path = path;
        this.size = size;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
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
        return "File{" +
                "fileId=" + fileId +
                ", fileType='" + fileType + '\'' +
                ", md5='" + md5 + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                '}';
    }
}
