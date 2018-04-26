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
 * @author peter
 */
@Data
@Entity
@Table(name = "fix_approach")
public class FixApproach implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FixApproachPK fixApproachPK;
    
}
