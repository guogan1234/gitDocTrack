package com.avp.mems.backstage.entity.basic;

import java.util.List;

/**
 * Created by Amber Wang on 2017-06-15 下午 06:02.
 */
public class LocationVo {
    private Location location;

    private List<LocationVo> childs;

    private Integer fineshedCount;

    private Integer notFineshedCount;

    private Integer Count;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getFineshedCount() {
        return fineshedCount;
    }

    public void setFineshedCount(Integer fineshedCount) {
        this.fineshedCount = fineshedCount;
    }

    public Integer getNotFineshedCount() {
        return notFineshedCount;
    }

    public void setNotFineshedCount(Integer notFineshedCount) {
        this.notFineshedCount = notFineshedCount;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public List<LocationVo> getChilds() {
        return childs;
    }

    public void setChilds(List<LocationVo> childs) {
        this.childs = childs;
    }
}
