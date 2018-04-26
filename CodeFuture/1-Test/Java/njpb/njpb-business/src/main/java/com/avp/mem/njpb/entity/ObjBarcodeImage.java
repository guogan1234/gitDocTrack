package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by six on 2017/7/24.
 */


@Entity
@Where(clause="remove_time is null")
@Table(name = "obj_barcode_image", schema = "bussiness", catalog = "mem-pb-dev")
public class ObjBarcodeImage extends EntityBase {

    private String barCodePath;
    private String barCodeSn;
    private String barCodeMessage;
    private Integer barCodeCategory;
    private Date exportTime;
    private Date activateTime;



    @Basic
    @Column(name = "bar_code_path")
    public String getBarCodePath() {
        return barCodePath;
    }

    public void setBarCodePath(String barCodePath) {
        this.barCodePath = barCodePath;
    }

    @Basic
    @Column(name = "bar_code_sn")
    public String getBarCodeSn() {
        return barCodeSn;
    }

    public void setBarCodeSn(String barCodeSn) {
        this.barCodeSn = barCodeSn;
    }

    @Basic
    @Column(name = "bar_code_message")
    public String getBarCodeMessage() {
        return barCodeMessage;
    }

    public void setBarCodeMessage(String barCodeMessage) {
        this.barCodeMessage = barCodeMessage;
    }

    @Basic
    @Column(name = "bar_code_category")
    public Integer getBarCodeCategory() {
        return barCodeCategory;
    }

    public void setBarCodeCategory(Integer barCodeCategory) {
        this.barCodeCategory = barCodeCategory;
    }

    @Basic
    @Column(name = "export_time")
    public Date getExportTime() {
        return exportTime;
    }

    public void setExportTime(Date exportTime) {
        this.exportTime = exportTime;
    }

    @Basic
    @Column(name = "activate_time")
    public Date getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(Date activateTime) {
        this.activateTime = activateTime;
    }


}
