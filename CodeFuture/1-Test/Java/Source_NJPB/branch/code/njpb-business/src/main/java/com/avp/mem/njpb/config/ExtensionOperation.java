/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.config;

import java.util.List;
import java.util.Map;

/**
 * Created by Amber Wang on 2017-08-01 下午 04:44.
 * 这个类用于封装流程配置文件里面的自定义属性值
 */
@Deprecated
public class ExtensionOperation {

    private String role;
    private List<String> operation;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getOperation() {
        return operation;
    }

    public void setOperation(List<String> operation) {
        this.operation = operation;
    }

}
