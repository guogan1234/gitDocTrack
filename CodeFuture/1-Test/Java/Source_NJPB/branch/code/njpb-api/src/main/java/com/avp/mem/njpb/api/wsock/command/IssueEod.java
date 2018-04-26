package com.avp.mem.njpb.api.wsock.command;

import com.avp.mem.njpb.api.wsock.DefaultRequest;

import java.util.Date;

/**
 * Created by boris feng on 2017/6/27.
 */

// use DefaultResponse
public class IssueEod extends DefaultRequest {
    private Integer eodNo;
    private String eodUrl;
    private String eodMd5;

    public IssueEod(Integer estateId, Integer msgSeq, Date reqTime, Integer eodNo, String eodUrl, String eodMd5) {
        super(estateId, MessageBook.ISSUE_EOD, msgSeq, reqTime);
        this.eodNo = eodNo;
        this.eodUrl = eodUrl;
        this.eodMd5 = eodMd5;
    }

    public Integer getEodNo() {
        return eodNo;
    }

    public void setEodNo(Integer eodNo) {
        this.eodNo = eodNo;
    }

    public String getEodUrl() {
        return eodUrl;
    }

    public void setEodUrl(String eodUrl) {
        this.eodUrl = eodUrl;
    }

    public String getEodMd5() {
        return eodMd5;
    }

    public void setEodMd5(String eodMd5) {
        this.eodMd5 = eodMd5;
    }
}
