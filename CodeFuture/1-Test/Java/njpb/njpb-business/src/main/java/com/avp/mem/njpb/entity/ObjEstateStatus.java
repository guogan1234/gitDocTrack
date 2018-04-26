package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause="remove_time is null")
@Table(name = "obj_estate_status", schema = "bussiness", catalog = "mem-pb-dev")
public class ObjEstateStatus extends EntityBase {

    private String statusName;
    private String statusNameEn;



    @Basic
    @Column(name = "status_name")
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Basic
    @Column(name = "status_name_en")
    public String getStatusNameEn() {
        return statusNameEn;
    }

    public void setStatusNameEn(String statusNameEn) {
        this.statusNameEn = statusNameEn;
    }

}
