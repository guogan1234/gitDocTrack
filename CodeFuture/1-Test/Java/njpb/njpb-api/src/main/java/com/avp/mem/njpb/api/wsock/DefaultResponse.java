package com.avp.mem.njpb.api.wsock;

import java.util.Date;

/**
 * Created by boris feng on 2017/6/27.
 */
public class DefaultResponse extends AbstraceMessage {
    private Integer result;
    private String message;
    private Date respTime;

    public DefaultResponse(Integer estateId, Integer msgId, String msgTag, Integer msgSeq, Integer result, String message, Date respTime) {

        super(estateId, msgId, msgTag, msgSeq);
        this.result = result;
        this.message = message;
        this.respTime = respTime;
    }

    public DefaultResponse(Integer estateId, MessageBook book, Integer msgSeq, Integer result, String message, Date respTime) {
        this(estateId, book.value(), book.phrase(), msgSeq, result, message, respTime);
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }




}
