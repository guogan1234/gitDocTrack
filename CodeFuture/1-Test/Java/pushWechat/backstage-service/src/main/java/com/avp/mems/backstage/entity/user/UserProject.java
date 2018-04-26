/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.user;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Entity
@Data
@Table(name = "user_project")
public class UserProject implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserProjectPK userProjectPK;

    public UserProjectPK getUserProjectPK() {
        return userProjectPK;
    }

    public void setUserProjectPK(UserProjectPK userProjectPK) {
        this.userProjectPK = userProjectPK;
    }
}
