package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause="remove_time is null")
@Table(name = "asso_user_project", schema = "bussiness", catalog = "mem-pb-dev")
public class AssoUserProject extends EntityBase {

    private Integer userId;
    private Integer projectId;

    public AssoUserProject() {
    }

    public AssoUserProject(Integer userId, Integer projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }



    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {

        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
