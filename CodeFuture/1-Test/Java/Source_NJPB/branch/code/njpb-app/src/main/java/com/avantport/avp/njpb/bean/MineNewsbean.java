package com.avantport.avp.njpb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by len on 2017/8/22.
 * 消息
 */

public class MineNewsbean extends BaseJson{

    /**
     * code : 40000
     * message : 查询成功
     * page : {"hasNext":false,"hasPrevious":false,"number":0,"size":20,"totalElements":2,"totalPages":1}
     * results : [{"author":"admin","createTime":1502957494883,"id":1,"lastUpdateTime":1502957494883,"messageAuthor":"zhou","messageTitle":"8月计划书","messageUrl":"www.baidu.com","receiveUserId":1,"receiveUserName":"admin","sendUserId":1,"sendUserName":"admin","status":0,"sysMessageId":1},{"author":"admin","createTime":1502957506020,"id":2,"lastUpdateTime":1502957506020,"messageAuthor":"zhou","messageTitle":"8月计划书","messageUrl":"www.baidu.com","receiveUserId":1,"receiveUserName":"admin","sendUserId":1,"sendUserName":"admin","status":0,"sysMessageId":1}]
     * timestamp : 1503383191923
     */

    /**
     * hasNext : false
     * hasPrevious : false
     * number : 0
     * size : 20
     * totalElements : 2
     * totalPages : 1
     */

    private PageBean page;
    /**
     * author : admin
     * createTime : 1502957494883
     * id : 1
     * lastUpdateTime : 1502957494883
     * messageAuthor : zhou
     * messageTitle : 8月计划书
     * messageUrl : www.baidu.com
     * receiveUserId : 1
     * receiveUserName : admin
     * sendUserId : 1
     * sendUserName : admin
     * status : 0
     * sysMessageId : 1
     */

    private List<ResultsBean> results;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class PageBean {
        private boolean hasNext;
        private boolean hasPrevious;
        private Integer number;
        private Integer size;
        private Integer totalElements;
        private Integer totalPages;

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public boolean isHasPrevious() {
            return hasPrevious;
        }

        public void setHasPrevious(boolean hasPrevious) {
            this.hasPrevious = hasPrevious;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Integer getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(Integer totalElements) {
            this.totalElements = totalElements;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }
    }

    public static class ResultsBean implements Serializable{
        private String author;
        private Long createTime;
        private Integer id;
        private Long lastUpdateTime;
        private String messageAuthor;
        private String messageTitle;
        private String messageUrl;
        private Integer receiveUserId;
        private String receiveUserName;
        private Integer sendUserId;
        private String sendUserName;
        private Integer status;
        private Integer sysMessageId;
        private String  messageText;
        private String messageFile3Url;
        private String messageFile2Url;

        public String getMessageFile3Url() {
            return messageFile3Url;
        }

        public void setMessageFile3Url(String messageFile3Url) {
            this.messageFile3Url = messageFile3Url;
        }

        public String getMessageFile2Url() {
            return messageFile2Url;
        }

        public void setMessageFile2Url(String messageFile2Url) {
            this.messageFile2Url = messageFile2Url;
        }

        public String getMessageFile1Url() {
            return messageFile1Url;
        }

        public void setMessageFile1Url(String messageFile1Url) {
            this.messageFile1Url = messageFile1Url;
        }

        private String messageFile1Url;

        public String getMessageText() {
            return messageText;
        }

        public void setMessageText(String messageText) {
            this.messageText = messageText;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getMessageAuthor() {
            return messageAuthor;
        }

        public void setMessageAuthor(String messageAuthor) {
            this.messageAuthor = messageAuthor;
        }

        public String getMessageTitle() {
            return messageTitle;
        }

        public void setMessageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
        }

        public String getMessageUrl() {
            return messageUrl;
        }

        public void setMessageUrl(String messageUrl) {
            this.messageUrl = messageUrl;
        }

        public Integer getReceiveUserId() {
            return receiveUserId;
        }

        public void setReceiveUserId(Integer receiveUserId) {
            this.receiveUserId = receiveUserId;
        }

        public String getReceiveUserName() {
            return receiveUserName;
        }

        public void setReceiveUserName(String receiveUserName) {
            this.receiveUserName = receiveUserName;
        }

        public Integer getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(Integer sendUserId) {
            this.sendUserId = sendUserId;
        }

        public String getSendUserName() {
            return sendUserName;
        }

        public void setSendUserName(String sendUserName) {
            this.sendUserName = sendUserName;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getSysMessageId() {
            return sysMessageId;
        }

        public void setSysMessageId(Integer sysMessageId) {
            this.sysMessageId = sysMessageId;
        }
    }
}
