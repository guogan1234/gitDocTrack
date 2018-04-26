/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author ambEr
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_work_order_resource", schema = "business")
public class VwWorkOrderResource extends EntityBase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "work_order_id")
    private Integer workOrderId;
    @Column(name = "file_id")
    private Integer fileId;
    @Column(name = "remark")
    private String remark;
    @Column(name = "category")
    private Integer category;
    @Column(name = "file_md5")
    private String fileMd5;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_size")
    private Integer fileSize;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "file_url")
    private String fileUrl;
    @Column(name = "create_by_user_name")
    private String createByUserName;
    @Column(name = "last_update_by_user_name")
    private String lastUpdateByUserName;

    public VwWorkOrderResource() {
    }

    public Integer getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(String createByUserName) {
        this.createByUserName = createByUserName;
    }

    public String getLastUpdateByUserName() {
        return lastUpdateByUserName;
    }

    public void setLastUpdateByUserName(String lastUpdateByUserName) {
        this.lastUpdateByUserName = lastUpdateByUserName;
    }

}
