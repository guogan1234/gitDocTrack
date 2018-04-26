package com.avp.mem.njpb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkOrderSerialNoUtil {

    /**
     * 由年月日时分秒+3位随机数 生成流水号
     *
     * @return
     */
    public static Long createSerialNo() {
        String t = getStringDate();
        int x = (int) (Math.random() * 1000) + 1;
        String serial = t + x;
        return Long.parseLong(serial);
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式yyyyMMddHHmmss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
