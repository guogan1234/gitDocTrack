/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.basic;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Entity
@Table(name = "profession")
@JsonSerialize
public class Profession implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public String getProfessionNo() {
        return professionNo;
    }

    public void setProfessionNo(String professionNo) {
        this.professionNo = professionNo;
    }

    public String getProfessionNameEn() {
        return professionNameEn;
    }

    public void setProfessionNameEn(String professionNameEn) {
        this.professionNameEn = professionNameEn;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "profession_name")
    private String professionName;
    @Column(name = "profession_no")
    private String professionNo;
    @Column(name = "profession_name_en")
    private String professionNameEn;


}
