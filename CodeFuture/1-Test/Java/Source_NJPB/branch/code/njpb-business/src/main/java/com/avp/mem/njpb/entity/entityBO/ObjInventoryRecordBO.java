/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.entityBO;

import com.avp.mem.njpb.entity.stock.ObjInventoryRecord;

import java.util.List;

/**
 * Created by six on 2017/8/3.
 */
public class ObjInventoryRecordBO extends ObjInventoryRecord {
    private List<String> barCodes;

    public List<String> getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(List<String> barCodes) {
        this.barCodes = barCodes;
    }
}
