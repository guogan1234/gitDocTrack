package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/15.
 */

public class MineBikeCheckbean extends BaseJson{
    /**
     * code : 40000
     * message : 查询成功
     * page : {"hasNext":false,"hasPrevious":false,"number":0,"size":20,"totalElements":6,"totalPages":1}
     * results : [{"category":0,"checkRemark":"123123123123","checkUserId":1,"corpId":1,"count":7,"createBy":1,"createTime":1502699130311,"estateName":"自行车2","estateNo":2,"estateSn":"10000003","estateStatusId":1,"id":8,"inventoryCheckRecordId":5,"lastUpdateTime":1502188702126,"paramVersion":1,"projectId":2,"stationName":"金科路","stationNo":"2","stationNoName":"2金科路"},{"category":1,"checkRemark":"123123123123","checkUserId":1,"corpId":1,"count":7,"createBy":1,"createTime":1502699130309,"estateName":"自行车1","estateNo":1,"estateSn":"10000002","estateStatusId":1,"id":7,"inventoryCheckRecordId":5,"lastUpdateTime":1502188702126,"paramVersion":1,"projectId":2,"stationId":5,"stationName":"金科路","stationNo":"2","stationNoName":"2金科路","supplierId":1},{"category":0,"checkRemark":"dsfsdfasdfgsdfgsd","checkUserId":1,"corpId":1,"count":5,"createBy":1,"createTime":1502698292102,"estateName":"自行车2","estateNo":2,"estateSn":"10000003","estateStatusId":1,"id":6,"inventoryCheckRecordId":3,"lastUpdateTime":1502188702126,"paramVersion":1,"projectId":2,"stationName":"金科路","stationNo":"2","stationNoName":"2金科路"},{"category":1,"checkRemark":"dsfsdfasdfgsdfgsd","checkUserId":1,"corpId":1,"count":5,"createBy":1,"createTime":1502698292100,"estateName":"自行车1","estateNo":1,"estateSn":"10000002","estateStatusId":1,"id":5,"inventoryCheckRecordId":3,"lastUpdateTime":1502188702126,"paramVersion":1,"projectId":2,"stationId":5,"stationName":"金科路","stationNo":"2","stationNoName":"2金科路","supplierId":1},{"category":0,"checkRemark":"lalaala","checkTime":1502264852397,"checkUserId":1,"corpId":1,"count":2,"createBy":1,"createTime":1502264852646,"estateName":"自行车3","estateNo":3,"estateSn":"10000004","estateStatusId":1,"id":4,"inventoryCheckRecordId":2,"lastUpdateTime":1502188702126,"paramVersion":1,"projectId":2,"stationName":"金科路","stationNo":"2","stationNoName":"2金科路"},{"category":0,"checkRemark":"lalaala","checkTime":1502264852397,"checkUserId":1,"corpId":1,"count":2,"createBy":1,"createTime":1502264852644,"estateName":"自行车2","estateNo":2,"estateSn":"10000003","estateStatusId":1,"id":3,"inventoryCheckRecordId":2,"lastUpdateTime":1502188702126,"paramVersion":1,"projectId":2,"stationName":"金科路","stationNo":"2","stationNoName":"2金科路"}]
     * timestamp : 1503474115023
     */

    /**
     * hasNext : false
     * hasPrevious : false
     * number : 0
     * size : 20
     * totalElements : 6
     * totalPages : 1
     */

    private PageBean page;
    /**
     * category : 0
     * checkRemark : 123123123123
     * checkUserId : 1
     * corpId : 1
     * count : 7
     * createBy : 1
     * createTime : 1502699130311
     * estateName : 自行车2
     * estateNo : 2
     * estateSn : 10000003
     * estateStatusId : 1
     * id : 8
     * inventoryCheckRecordId : 5
     * lastUpdateTime : 1502188702126
     * paramVersion : 1
     * projectId : 2
     * stationName : 金科路
     * stationNo : 2
     * stationNoName : 2金科路
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

    public static class ResultsBean {
        private Integer category;
        private String checkRemark;
        private Integer checkUserId;
        private Integer corpId;
        private Integer count;
        private Integer createBy;
        private Long createTime;
        private String estateName;
        private Integer estateNo;
        private String estateSn;
        private Integer estateStatusId;
        private Integer id;
        private Integer inventoryCheckRecordId;
        private Long lastUpdateTime;
        private Integer paramVersion;
        private Integer projectId;
        private String stationName;
        private String stationNo;
        private String stationNoName;

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public String getCheckRemark() {
            return checkRemark;
        }

        public void setCheckRemark(String checkRemark) {
            this.checkRemark = checkRemark;
        }

        public Integer getCheckUserId() {
            return checkUserId;
        }

        public void setCheckUserId(Integer checkUserId) {
            this.checkUserId = checkUserId;
        }

        public Integer getCorpId() {
            return corpId;
        }

        public void setCorpId(Integer corpId) {
            this.corpId = corpId;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Integer createBy) {
            this.createBy = createBy;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getEstateName() {
            return estateName;
        }

        public void setEstateName(String estateName) {
            this.estateName = estateName;
        }

        public Integer getEstateNo() {
            return estateNo;
        }

        public void setEstateNo(Integer estateNo) {
            this.estateNo = estateNo;
        }

        public String getEstateSn() {
            return estateSn;
        }

        public void setEstateSn(String estateSn) {
            this.estateSn = estateSn;
        }

        public Integer getEstateStatusId() {
            return estateStatusId;
        }

        public void setEstateStatusId(Integer estateStatusId) {
            this.estateStatusId = estateStatusId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getInventoryCheckRecordId() {
            return inventoryCheckRecordId;
        }

        public void setInventoryCheckRecordId(Integer inventoryCheckRecordId) {
            this.inventoryCheckRecordId = inventoryCheckRecordId;
        }

        public Long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public Integer getParamVersion() {
            return paramVersion;
        }

        public void setParamVersion(Integer paramVersion) {
            this.paramVersion = paramVersion;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getStationNo() {
            return stationNo;
        }

        public void setStationNo(String stationNo) {
            this.stationNo = stationNo;
        }

        public String getStationNoName() {
            return stationNoName;
        }

        public void setStationNoName(String stationNoName) {
            this.stationNoName = stationNoName;
        }
    }

    //我的盘点，存储我的盘点的数据的地方

}
