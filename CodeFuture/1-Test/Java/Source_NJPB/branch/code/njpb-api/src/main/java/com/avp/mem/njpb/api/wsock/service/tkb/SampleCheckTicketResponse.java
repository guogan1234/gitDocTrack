package com.avp.mem.njpb.api.wsock.service.tkb;

import com.avp.mem.njpb.api.wsock.DefaultResponse;

import java.util.Date;

/**
 * Created by boris feng on 2017/6/27.
 */
public class SampleCheckTicketResponse extends DefaultResponse {
    private String serviceToken;

    public String getServiceToken() {
        return serviceToken;
    }

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    public SampleCheckTicketResponse(Integer estateId, Integer msgSeq, Integer code, String message, Date respTime, String serviceToken) {
        super(estateId, MessageBook.REGISTER, msgSeq, code, message, respTime);
        this.serviceToken = serviceToken;
    }
}
