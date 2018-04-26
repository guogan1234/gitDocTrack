package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause="remove_time is null")
@Table(name = "sys_param", schema = "bussiness", catalog = "mem-pb-dev")
public class SysParam extends EntityBase {
    private Integer levelOneResponseTime;
    private Integer levelOneRepairTime;
    private Integer levelTwoResponseTime;
    private Integer levelTwoRepairTime;
    private Integer levelThreeResponseTime;
    private Integer levelThreeRepairTime;
    private Date workStartTime;
    private Date workEndTime;
    private Integer workPeriod;

    @Basic
    @Column(name = "level_one_response_time")
    public Integer getLevelOneResponseTime() {
        return levelOneResponseTime;
    }

    public void setLevelOneResponseTime(Integer levelOneResponseTime) {
        this.levelOneResponseTime = levelOneResponseTime;
    }

    @Basic
    @Column(name = "level_one_repair_time")
    public Integer getLevelOneRepairTime() {
        return levelOneRepairTime;
    }

    public void setLevelOneRepairTime(Integer levelOneRepairTime) {
        this.levelOneRepairTime = levelOneRepairTime;
    }

    @Basic
    @Column(name = "level_two_response_time")
    public Integer getLevelTwoResponseTime() {
        return levelTwoResponseTime;
    }

    public void setLevelTwoResponseTime(Integer levelTwoResponseTime) {
        this.levelTwoResponseTime = levelTwoResponseTime;
    }

    @Basic
    @Column(name = "level_two_repair_time")
    public Integer getLevelTwoRepairTime() {
        return levelTwoRepairTime;
    }

    public void setLevelTwoRepairTime(Integer levelTwoRepairTime) {
        this.levelTwoRepairTime = levelTwoRepairTime;
    }

    @Basic
    @Column(name = "level_three_response_time")
    public Integer getLevelThreeResponseTime() {
        return levelThreeResponseTime;
    }

    public void setLevelThreeResponseTime(Integer levelThreeResponseTime) {
        this.levelThreeResponseTime = levelThreeResponseTime;
    }

    @Basic
    @Column(name = "level_three_repair_time")
    public Integer getLevelThreeRepairTime() {
        return levelThreeRepairTime;
    }

    public void setLevelThreeRepairTime(Integer levelThreeRepairTime) {
        this.levelThreeRepairTime = levelThreeRepairTime;
    }

    @Basic
    @Column(name = "work_start_time")
    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    @Basic
    @Column(name = "work_end_time")
    public Date getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }

    @Basic
    @Column(name = "work_period")
    public Integer getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Integer workPeriod) {
        this.workPeriod = workPeriod;
    }

}
