/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.work;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * Created by Amber Wang on 2017-05-27
 */
@Data
@Entity
@Table(name = "bad_component")
public class BadComponent implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BadComponentPK badComponentPK;

    public BadComponentPK getBadComponentPK() {
        return badComponentPK;
    }

    public void setBadComponentPK(BadComponentPK badComponentPK) {
        this.badComponentPK = badComponentPK;
    }
}
