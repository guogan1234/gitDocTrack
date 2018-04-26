/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Data
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "city_id")
    private Long cityId;
    @Size(max = 50)
    @Column(name = "name_cn")
    private String nameCn;
    @Size(max = 30)
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "level")
    private Short level;
    @Column(name = "enabled",insertable = false)
    private Boolean enabled;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;

}
