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

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Data
@Entity
@Table(name = "fault_type")
public class FaultType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "component_type_id")
    private Long componentTypeId;
    @Size(max = 50)
    @Column(name = "name_cn")
    private String nameCn;
    @Size(max = 50)
    @Column(name = "name_en")
    private String nameEn;
    @Size(max = 10)
    @Column(name = "logic_index")
    private String logicIndex;
    @Size(max = 10)
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "short_mnemonic")
    private Character shortMnemonic;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    
//    @JoinColumn(name = "component_type_id", referencedColumnName = "id")
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    public List<ComponentType> componentTypes = new ArrayList<>();


}
