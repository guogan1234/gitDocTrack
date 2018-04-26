/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.view;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by len on 2017/10/10.
 */
@Entity

@Table(name = "vw_score_month", schema = "business")
public class VwScoreMonth implements Serializable {
    private String scoreMonth;
    private Double workOrderScoreCount;
    private Integer uid;

    @Id
    @Column(
            name = "id", unique = true, nullable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;

    @Basic
    @Column(name = "id")
    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    @Basic
    @Column(name = "score_month")
    public String getScoreMonth() {
        return scoreMonth;
    }

    public void setScoreMonth(String scoreMonth) {
        this.scoreMonth = scoreMonth;
    }

    @Basic
    @Column(name = "work_order_score_count")
    public Double getWorkOrderScoreCount() {
        return workOrderScoreCount;
    }

    public void setWorkOrderScoreCount(Double workOrderScoreCount) {
        this.workOrderScoreCount = workOrderScoreCount;
    }
    @Basic
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }


}
