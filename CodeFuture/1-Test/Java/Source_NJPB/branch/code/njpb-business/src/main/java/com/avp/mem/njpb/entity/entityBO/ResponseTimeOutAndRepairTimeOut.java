/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.entityBO;

import java.util.Date;

/**
 * Created by Amber Wang on 2017-09-07 下午 05:41.
 */
public class ResponseTimeOutAndRepairTimeOut {
    private Date responseTimeOut;

    private Date repairTimeOut;

    public ResponseTimeOutAndRepairTimeOut(Date responseTimeOut, Date repairTimeOut) {
        this.responseTimeOut = responseTimeOut;
        this.repairTimeOut = repairTimeOut;
    }

    public Date getResponseTimeOut() {
        return responseTimeOut;
    }

    public void setResponseTimeOut(Date responseTimeOut) {
        responseTimeOut = responseTimeOut;
    }

    public Date getRepairTimeOut() {
        return repairTimeOut;
    }

    public void setRepairTimeOut(Date repairTimeOut) {
        repairTimeOut = repairTimeOut;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseTimeOutAndRepairTimeOut{");
        sb.append("responseTimeOut=").append(responseTimeOut);
        sb.append(", repairTimeOut=").append(repairTimeOut);
        sb.append('}');
        return sb.toString();
    }
}
