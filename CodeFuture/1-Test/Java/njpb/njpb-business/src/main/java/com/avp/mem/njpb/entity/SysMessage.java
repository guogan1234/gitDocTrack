package com.avp.mem.njpb.entity;

import com.avp.mem.njpb.api.entity.EntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause="remove_time is null")
@Table(name = "sys_message", schema = "bussiness", catalog = "mem-pb-dev")
public class SysMessage extends EntityBase {
    private String messageTitle;
    private String messageAuthor;
    private String messageUrl;

    @Basic
    @Column(name = "message_title")
    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    @Basic
    @Column(name = "message_author")
    public String getMessageAuthor() {
        return messageAuthor;
    }

    public void setMessageAuthor(String messageAuthor) {
        this.messageAuthor = messageAuthor;
    }

    @Basic
    @Column(name = "message_url")
    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

}
