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
import lombok.Data;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Embeddable
@Data
public class ProjectLocationPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "project_id")
    private long projectId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "location_id")
    private long locationId;

}
