package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Table(name = "obj_station", schema = "bussiness", catalog = "mem-pb-dev")
public class ObjStation extends EntityBase {
    private Integer projectId;
    private String stationNo;
    private String stationSn;
    private String stationName;
    private String stationEn;
    private String stationNameShort;
    private Double longitude;
    private Double latitude;

    @Basic
    @Column(name = "project_id")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "station_no")
    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    @Basic
    @Column(name = "station_sn")
    public String getStationSn() {
        return stationSn;
    }

    public void setStationSn(String stationSn) {
        this.stationSn = stationSn;
    }

    @Basic
    @Column(name = "station_name")
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Basic
    @Column(name = "station_en")
    public String getStationEn() {
        return stationEn;
    }

    public void setStationEn(String stationEn) {
        this.stationEn = stationEn;
    }

    @Basic
    @Column(name = "station_name_short")
    public String getStationNameShort() {
        return stationNameShort;
    }

    public void setStationNameShort(String stationNameShort) {
        this.stationNameShort = stationNameShort;
    }

    @Basic
    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}
