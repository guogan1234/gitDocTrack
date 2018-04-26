package com.avp.mem.njpb.api.wsock;

/**
 * Created by boris feng on 2017/6/27.
 */
abstract public class AbstraceMessage {
    public enum MessageBook {
        REGISTER(101, "Register"),

        ISSUE_EOD(201, "IssueEod");

        private final Integer value;
        private final String phrase;

        private MessageBook(int value, String phrase) {
            this.value = value;
            this.phrase = phrase;
        }

        public Integer value() {
            return this.value;
        }

        public String phrase() {
            return this.phrase;
        }
    }

    private Integer estateId;
    private Integer msgId;
    private String msgTag;
    private Integer msgSeq;

    public AbstraceMessage(Integer estateId, Integer msgId, String msgTag, Integer msgSeq) {
        this.estateId = estateId;
        this.msgId = msgId;
        this.msgTag = msgTag;
        this.msgSeq = msgSeq;
    }

    public Integer getEstateId() {
        return estateId;
    }

    public void setEstateId(Integer estateId) {
        this.estateId = estateId;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getMsgTag() {
        return msgTag;
    }

    public void setMsgTag(String msgTag) {
        this.msgTag = msgTag;
    }

    public Integer getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(Integer msgSeq) {
        this.msgSeq = msgSeq;
    }
}
