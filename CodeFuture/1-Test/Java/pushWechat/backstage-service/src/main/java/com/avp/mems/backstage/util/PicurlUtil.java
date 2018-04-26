package com.avp.mems.backstage.util;

/**
 * Created by len on 2017/6/23.
 */
public class PicurlUtil {

    public static String picurlRound(Integer count, String url) {
        String picurl = "";
        if ((count % 6) == 1) {
            picurl = "http://" + url + "/default/image/7.png";
        }
        if ((count % 6) == 2) {
            picurl = "http://" + url + "/default/image/8.png";
        }
        if ((count % 6) == 3) {
            picurl = "http://" + url + "/default/image/9.png";
        }
        if ((count % 6) == 4) {
            picurl = "http://" + url + "/default/image/10.png";
        }
        if ((count % 6) == 5) {
            picurl = "http://" + url + "/default/image/11.png";
        }
        if ((count % 6) == 0) {
            picurl = "http://" + url + "/default/image/12.png";
        }
        return picurl;
    }
}
