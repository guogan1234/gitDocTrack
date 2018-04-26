/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.system;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by six on 2017/7/24.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "sys_message", schema = "business")
public class SysMessage extends EntityBase {
    @Column(name = "message_title")
    private String messageTitle;
    @Column(name = "message_author")
    private String messageAuthor;
    @Column(name = "message_text")
    private String messageText;
    @Column(name = "status")
    private Integer status;
    @Column(name = "message_file1_url")
    private String messageFile1Url;
    @Column(name = "message_file2_url")
    private String messageFile2Url;
    @Column(name = "message_file3_url")
    private String messageFile3Url;

    public SysMessage() {
    }


    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageAuthor() {
        return messageAuthor;
    }

    public void setMessageAuthor(String messageAuthor) {
        this.messageAuthor = messageAuthor;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessageFile1Url() {
        return messageFile1Url;
    }

    public void setMessageFile1Url(String messageFile1Url) {
        this.messageFile1Url = messageFile1Url;
    }

    public String getMessageFile2Url() {
        return messageFile2Url;
    }

    public void setMessageFile2Url(String messageFile2Url) {
        this.messageFile2Url = messageFile2Url;
    }

    public String getMessageFile3Url() {
        return messageFile3Url;
    }

    public void setMessageFile3Url(String messageFile3Url) {
        this.messageFile3Url = messageFile3Url;
    }
}

