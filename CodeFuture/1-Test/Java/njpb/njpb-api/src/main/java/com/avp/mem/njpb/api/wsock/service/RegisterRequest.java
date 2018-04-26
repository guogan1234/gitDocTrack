package com.avp.mem.njpb.api.wsock.service;

import com.avp.mem.njpb.api.wsock.DefaultRequest;

import java.util.Date;

/**
 * Created by boris feng on 2017/6/27.
 */
public class RegisterRequest extends DefaultRequest {
    private String estateToken;

    public String getEstateToken() {
        return estateToken;
    }

    public void setEstateToken(String estateToken) {
        this.estateToken = estateToken;
    }

    public RegisterRequest(Integer estateId, Integer msgSeq, Date reqTime, String estateToken) {

        super(estateId, MessageBook.REGISTER, msgSeq, reqTime);
        this.estateToken = estateToken;
    }
}
