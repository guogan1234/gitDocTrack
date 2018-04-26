/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.work;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Amber Wang on 2017-05-27
 */
@Entity
@Table(name = "version")
public class Version implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "version_no")
    private String versionNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version_type")
    private long versionType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private long status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "download_url")
    private String downloadUrl;
    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @Column(name = "release_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseTime;

}
