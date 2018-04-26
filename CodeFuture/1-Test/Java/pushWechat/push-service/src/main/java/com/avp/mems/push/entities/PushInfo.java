/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.push.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author ambEr
 */
@Data
@Entity
@Table(name = "push_info")
public class PushInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PushInfoPK pushInfoPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "device_model")
    private String deviceModel;
    @Column(name = "device_vendor")
    private String deviceVendor;
    @Column(name = "os_version")
    private String osVersion;
}
