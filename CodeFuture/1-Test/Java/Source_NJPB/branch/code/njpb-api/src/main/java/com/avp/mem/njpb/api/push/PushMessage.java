package com.avp.mem.njpb.api.push;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris feng on 2017/1/17.
 */
public class PushMessage {
    private List<Integer> userIds = new ArrayList<>();
    private String title = "";
    private String content = "";
    private String app = "";

    // reserved
//    private Map<String, Object> params = new HashMap<>();

//    public void setParam(String key, Object val) {
//        this.params.put(key, val);
//    }

    public PushMessage() {

    }

    public PushMessage(String title, String content, List<Integer> userIds) {
        this.title = title;
        this.content = content;
        this.userIds.addAll(userIds);
    }

    public String getTitle() {
        return title;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }

    public String getContent() {
        return content;
    }

    public List<Integer> getUserIds() {
        return  this.userIds;
    }

//    public void setContent(String content) {
//        this.content = content;
//    }

//    @Override
//    public String toString() {
//        return "title:" + title + "; content:" + content;
//    }
}
