/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.basic;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "obj_file", schema = "business")
public class ObjFile extends EntityBase {
    private String fileId;
    private Integer fileStatus;
    private String fileName;
    private Integer fileSize;
    private String thumbnail;
    private String fileType;
    private String fileUrl;

    public ObjFile() {
    }

    public ObjFile(Integer id, String fileId, String fileName) {
        this.fileId = fileId;
        this.fileName = fileName;
    }

    public ObjFile(String fileId, String fileName, Integer fileSize, String thumbnail,
                   String fileType, Integer createBy) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.thumbnail = thumbnail;
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "file_id")
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "file_status")
    public Integer getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
    }

    @Basic
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "file_size")
    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    @Basic
    @Column(name = "thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Basic
    @Column(name = "file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Column(name = "file_url")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ObjFile{");
        sb.append("fileId='").append(fileId).append('\'');
        sb.append(", fileStatus=").append(fileStatus);
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", fileSize=").append(fileSize);
        sb.append(", thumbnail='").append(thumbnail).append('\'');
        sb.append(", fileType='").append(fileType).append('\'');
        sb.append(", fileUrl='").append(fileUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
