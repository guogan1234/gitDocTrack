/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.basic;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Data
@Entity
@Table(name = "equipment_type")
public class EquipmentType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "short_name")
    private String shortName;
    @Size(max = 50)
    @Column(name = "name_cn")
    private String nameCn;
    @Size(max = 50)
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "short_mnemonic")
    private Character shortMnemonic;
    @Basic(optional = false)
    @NotNull
    @Column(name = "managed")
    private short managed;
    @Column(name = "connect_type")
    private Short connectType;
    @Size(max = 20)
    @Column(name = "com_protocol")
    private String comProtocol;

}
