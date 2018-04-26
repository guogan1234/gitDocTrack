/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avp.mems.backstage.entity.push;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author GD
 */
public class Message {

    /**
     * 要推送的消息标题。
     */
    private String title = "";


    private Long workOrderId;

    private List<String> topartys;

    private String url;//链接地址

    private String picurl;//企业微信图片地址

    private String wechatcontent = "";

    private String wechattitle = "";

    private String status;


    /**
     * 要推送的消息内容。
     */
    private String content = "";
    
    private String sender = "";
    
    private String command = "";

    /**
     * 需要推送的目标App内部名称列表。targetApps列表应用以下规则：
     * 1、App名称为系统内部标识符，并非推送引擎的App ID。
     * 2、至少包含一个列表项，不可为null，且长度不可为0。
     * 3、当targetApps中包含多个App名称时，将忽略targetUsers与targetRoles，对所有App的所有用户发送广播消息。
     * 4、当targetUsers或targetRoles长度不为0（或列表不为null）时，targetApps中仅第一个App名称有效。
     * 5、当targetUsers与targetRoles长度为0（或列表为null）时，且targetApps中仅有一个App名称时，对该App所有用户发送广播消息。
     */
    private List<String> targetApps = new ArrayList<>();

    /**
     * 需要接收消息的目标用户名列表。规则如下：
     * targetUsers与targetRoles共同确定需要接受消息的最终用户。
     * 当targetUsers与targetRoles长度均为0（或均为null）时，按应用广播消息
     * 
     */
    private List<String> targetUsers = new ArrayList<>();
    
    /**
     * 需要接收消息的目标角色列表。规则同targetUsers
     */
    private List<String> targetRoles = new ArrayList<>();
    
    private Map<String, String> args = new LinkedHashMap<>();

    private String isWechatWorkorder;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the targetApps
     */
    public List<String> getTargetApps() {
        return targetApps;
    }

    /**
     * @param targetApps the targetApps to set
     */
    public void setTargetApps(List<String> targetApps) {
        this.targetApps = targetApps;
    }

    /**
     * @return the targetUsers
     */
    public List<String> getTargetUsers() {
        return targetUsers;
    }

    /**
     * @param targetUsers the targetUsers to set
     */
    public void setTargetUsers(List<String> targetUsers) {
        this.targetUsers = targetUsers;
    }

    /**
     * @return the targetRoles
     */
    public List<String> getTargetRoles() {
        return targetRoles;
    }

    /**
     * @param targetRoles the targetRoles to set
     */
    public void setTargetRoles(List<String> targetRoles) {
        this.targetRoles = targetRoles;
    }

    /**
     * @return the args
     */
    public Map<String, String> getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public List<String> getTopartys() {
        return topartys;
    }

    public void setTopartys(List<String> topartys) {
        this.topartys = topartys;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getWechatcontent() {
        return wechatcontent;
    }

    public void setWechatcontent(String wechatcontent) {
        this.wechatcontent = wechatcontent;
    }

    public String getWechattitle() {
        return wechattitle;
    }

    public void setWechattitle(String wechattitle) {
        this.wechattitle = wechattitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsWechatWorkorder() {
        return isWechatWorkorder;
    }

    public void setIsWechatWorkorder(String isWechatWorkorder) {
        this.isWechatWorkorder = isWechatWorkorder;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", workOrderId=" + workOrderId +
                ", topartys=" + topartys +
                ", url='" + url + '\'' +
                ", picurl='" + picurl + '\'' +
                ", wechatcontent='" + wechatcontent + '\'' +
                ", wechattitle='" + wechattitle + '\'' +
                ", status='" + status + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", command='" + command + '\'' +
                ", targetApps=" + targetApps +
                ", targetUsers=" + targetUsers +
                ", targetRoles=" + targetRoles +
                ", args=" + args +
                ", isWechatWorkorder='" + isWechatWorkorder + '\'' +
                '}';
    }
}
