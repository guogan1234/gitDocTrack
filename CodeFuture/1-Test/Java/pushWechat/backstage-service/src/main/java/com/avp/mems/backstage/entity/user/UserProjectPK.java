/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.user;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Data
@Embeddable
public class UserProjectPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "project_name")
    private int projectName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getProjectName() {
        return projectName;
    }

    public void setProjectName(int projectName) {
        this.projectName = projectName;
    }
}
