package com.avp.mem.njpb.api.wsock.service.tkb;

import com.avp.mem.njpb.api.wsock.DefaultRequest;

import java.util.Date;

/**
 * Created by boris feng on 2017/7/7.
 */

public class SampleValidateTicketRequest extends DefaultRequest{

    private String estateToken;


    public SampleValidateTicketRequest(Integer estateId, Integer msgSeq, Date reqTime, String estateToken) {

        super(estateId, MessageBook.REGISTER, msgSeq, reqTime);
        this.estateToken = estateToken;
    }
}
