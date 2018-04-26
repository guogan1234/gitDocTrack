package com.avantport.avp.njpb.bean;

/**
 * Created by len on 2017/8/25.
 */

public class PutRequestbean {

    /**
     * code : 0
     * message : 申请领料/归还被驳回，无法领取
     * timestamp : 1502870054794
     */

    private Integer code;
    private String message;
    private long timestamp;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
