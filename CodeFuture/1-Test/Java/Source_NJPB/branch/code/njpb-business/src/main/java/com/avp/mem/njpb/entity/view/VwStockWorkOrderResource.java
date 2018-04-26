/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by len on 2017/10/30.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_stock_work_order_resource", schema = "business", catalog = "mem-pb-dev")
public class VwStockWorkOrderResource extends EntityBase {
    private Integer stockWorkOrderId;
    private Integer fileId;
    private String remark;
    private Integer category;
    private String fileMd5;
    private String fileName;
    private Integer fileSize;
    private String thumbnail;
    private String fileType;
    private String fileUrl;
    private String createByUserName;
    private String lastUpdateByUserName;


    @Basic
    @Column(name = "stock_work_order_id")
    public Integer getStockWorkOrderId() {
        return stockWorkOrderId;
    }

    public void setStockWorkOrderId(Integer stockWorkOrderId) {
        this.stockWorkOrderId = stockWorkOrderId;
    }

    @Basic
    @Column(name = "file_id")
    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "category")
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }


    @Basic
    @Column(name = "file_md5")
    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
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

    @Basic
    @Column(name = "file_url")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Basic
    @Column(name = "create_by_user_name")
    public String getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(String createByUserName) {
        this.createByUserName = createByUserName;
    }

    @Basic
    @Column(name = "last_update_by_user_name")
    public String getLastUpdateByUserName() {
        return lastUpdateByUserName;
    }

    public void setLastUpdateByUserName(String lastUpdateByUserName) {
        this.lastUpdateByUserName = lastUpdateByUserName;
    }

}
