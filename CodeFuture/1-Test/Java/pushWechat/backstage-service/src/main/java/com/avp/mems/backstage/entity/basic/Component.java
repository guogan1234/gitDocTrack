/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.basic;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Data
@Entity
@Table(name = "component")
public class Component implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @Column(name = "type_id")
    private Long typeId;
    @Size(max = 50)
    @Column(name = "device_id")
    private String deviceId;
    @Size(max = 50)
    @Column(name = "bar_code")
    private String barCode;
    @Column(name = "logical_id")
    private Short logicalId;
    @Size(max = 50)
    @Column(name = "name_cn")
    private String nameCn;
    @Size(max = 50)
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "creation_date" ,insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "removed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date removedDate;
    @Column(name = "last_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
    @Column(name = "purchase_date")
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
    @Column(name = "supplier_id")
    private Long supplierId;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "level")
    private Short level;

}
