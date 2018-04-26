package com.avantport.avp.njpb.bean;

/**
 * Created by len on 2017/9/7.
 */

public class PartsApplyAndReturn {

    String name;
    Integer estateType;
    String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEstateType() {
        return estateType;
    }

    public void setEstateType(Integer estateType) {
        this.estateType = estateType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }



    public PartsApplyAndReturn(String name, Integer estateType, String count) {
        this.name = name;
        this.estateType = estateType;
        this.count = count;
    }
}
