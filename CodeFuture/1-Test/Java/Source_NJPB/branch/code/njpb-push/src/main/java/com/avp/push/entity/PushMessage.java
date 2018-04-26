package com.avp.push.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boris feng on 2017/1/17.
 */
public class PushMessage {
    private List<Integer> userIds = new ArrayList<>();
    private String title = "";
    private String content = "";

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



    public String getContent() {
        return content;
    }

    public List<Integer> getUserIds() {
        return  this.userIds;
    }


}
