/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.entityBO;

import com.avp.mem.njpb.entity.view.VwEstateModuleType;

/**
 * Created by Amber Wang on 2017-09-06 下午 05:25.
 */
public class ModuleTypeTreeNode {
    private String id;
    private String parent;
    private String text;
    private VwEstateModuleType vwEstateModuleType;

    public ModuleTypeTreeNode(String id, String parent, String text, VwEstateModuleType vwEstateModuleType) {
        this.id = id;
        this.parent = parent;
        this.text = text;
        this.vwEstateModuleType = vwEstateModuleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public VwEstateModuleType getVwEstateModuleType() {
        return vwEstateModuleType;
    }

    public void setVwEstateModuleType(VwEstateModuleType vwEstateModuleType) {
        this.vwEstateModuleType = vwEstateModuleType;
    }
}
