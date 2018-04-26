package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause="remove_time is null")
@Table(name = "obj_corporation", schema = "bussiness", catalog = "mem-pb-dev")
public class ObjCorporation extends EntityBase {
    private String corpName;
    private Integer corpNo;
    private String corpAddr;
    private String corpContactName;
    private String corpContactTel;
    private Integer corpLevel;




    @Basic
    @Column(name = "corp_name")
    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    @Basic
    @Column(name = "corp_no")
    public Integer getCorpNo() {
        return corpNo;
    }

    public void setCorpNo(Integer corpNo) {
        this.corpNo = corpNo;
    }

    @Basic
    @Column(name = "corp_addr")
    public String getCorpAddr() {
        return corpAddr;
    }

    public void setCorpAddr(String corpAddr) {
        this.corpAddr = corpAddr;
    }

    @Basic
    @Column(name = "corp_contact_name")
    public String getCorpContactName() {
        return corpContactName;
    }

    public void setCorpContactName(String corpContactName) {
        this.corpContactName = corpContactName;
    }

    @Basic
    @Column(name = "corp_contact_tel")
    public String getCorpContactTel() {
        return corpContactTel;
    }

    public void setCorpContactTel(String corpContactTel) {
        this.corpContactTel = corpContactTel;
    }

    @Basic
    @Column(name = "corp_level")
    public Integer getCorpLevel() {
        return corpLevel;
    }

    public void setCorpLevel(Integer corpLevel) {
        this.corpLevel = corpLevel;
    }


}
