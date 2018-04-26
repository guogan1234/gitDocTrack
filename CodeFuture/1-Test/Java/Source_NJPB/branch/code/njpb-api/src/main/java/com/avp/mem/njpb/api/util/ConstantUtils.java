package com.avp.mem.njpb.api.util;

/**
 * Created by boris feng on 2017/7/7.
 */
public enum ConstantUtils {

    // RefTxnType
    TXN_TYPE_TICKET_CHECKED(1000, "检票"),
    TXN_TYPE_TICKET_VALIDATED(1010, "验票"),
    // END RefTxnType

    //RefTicketCheckedType
    TICKET_CHECKED_TYPE_CHECK(100, "检票"),
    TICKET_CHECKED_TYPE_VALIDATE(200, "验票"),
    //END RefTicketCheckedType

    //RefTicketCheckedMode
    TICKET_CHECK_MODE_BARCODE(1, "二维码检票"),
    TICKET_CHECK_MODE_IDCARD(2, "身份证检票"),
    TICKET_CHECK_MODE_ICCARD(4, "IC卡检票"),
    TICKET_CHECK_MODE_FACE(8, "人证合一"),
    //END RefTicketCheckedMode

    //标准票务返回结果
    TXN_OK(0, "成功"),
    TXN_FAILED(-1, "失败"),
    TXN_TIMEOUT(-1000, "票务超时"),
    TXN_MISSING(-2000, "参数缺失"),
    TXN_ILLEGAL(-3000, "非法调用"),
    TXN_DEPRECATED(100, "过期接口"),

    //END

    UNDEFINED_CONSTANT(-9999, "未知");

    private final Integer value;
    private final String phrase;

    private ConstantUtils(Integer value, String phrase) {
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
