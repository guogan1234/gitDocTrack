/**
 * Copyright (c) 2017 Avant-Port All Rights Reserved.
 */
package com.avp.mem.njpb.entity.basic;

import com.avp.mem.njpb.api.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by six on 2017/8/17.
 */
@Entity
@Where(clause = "remove_time is null")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vw_message_send_detail", schema = "business")
public class VwMessageSendDetail extends EntityBase {

    private Integer sendUserId;
    private String sendUserName;
    private Integer receiveUserId;
    private String receiveUserName;
    private Integer sysMessageId;
    private String messageTitle;
    private String messageAuthor;
    private String author;
    private String messageText;
    private Integer status;
//    private String messageFile1Url;
//    private String messageFile2Url;
//    private String messageFile3Url;

    @Basic
    @Column(name = "send_user_id")
    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    @Basic
    @Column(name = "send_user_name")
    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    @Basic
    @Column(name = "receive_user_id")
    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    @Basic
    @Column(name = "receive_user_name")
    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    @Basic
    @Column(name = "sys_message_id")
    public Integer getSysMessageId() {
        return sysMessageId;
    }

    public void setSysMessageId(Integer sysMessageId) {
        this.sysMessageId = sysMessageId;
    }

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
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "message_text")
    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Basic
    @Column(name = "status")

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

//    @Basic
//    @Column(name = "message_file1_url")
//    public String getMessageFile1Url() {
//        return messageFile1Url;
//    }
//
//    public void setMessageFile1Url(String messageFile1Url) {
//        this.messageFile1Url = messageFile1Url;
//    }
//    @Basic
//    @Column(name = "message_file2_url")
//    public String getMessageFile2Url() {
//        return messageFile2Url;
//    }
//
//    public void setMessageFile2Url(String messageFile2Url) {
//        this.messageFile2Url = messageFile2Url;
//    }
//    @Basic
//    @Column(name = "message_file3_url")
//    public String getMessageFile3Url() {
//        return messageFile3Url;
//    }
//
//    public void setMessageFile3Url(String messageFile3Url) {
//        this.messageFile3Url = messageFile3Url;
//    }
}
