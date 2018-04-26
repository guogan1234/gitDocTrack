package com.avp.mem.njpb.api.wsock;

import java.util.Date;

/**
 * Created by boris feng on 2017/6/27.
 */
public class DefaultRequest extends AbstraceMessage {
    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    private Date reqTime;

    public DefaultRequest(Integer estateId, Integer msgId, String msgTag, Integer msgSeq, Date reqTime) {
        super(estateId, msgId, msgTag, msgSeq);
        this.reqTime = reqTime;
    }

    public DefaultRequest(Integer estateId, MessageBook book, Integer msgSeq, Date reqTime) {
        this(estateId, book.value(), book.phrase(), msgSeq, reqTime);
    }
}
