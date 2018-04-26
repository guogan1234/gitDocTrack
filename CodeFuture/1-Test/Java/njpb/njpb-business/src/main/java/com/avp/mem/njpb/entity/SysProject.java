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
@Table(name = "sys_project", schema = "bussiness", catalog = "mem-pb-dev")
public class SysProject extends EntityBase {
    private String projectName;
    private Integer cropId;



    @Basic
    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "crop_id")
    public Integer getCropId() {
        return cropId;
    }

    public void setCropId(Integer cropId) {
        this.cropId = cropId;
    }
}
