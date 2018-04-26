package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/31.
 */

public class StationInfobean extends BaseJson{

    /**
     * code : 40000
     * message : 查询成功
     * page : {"hasNext":false,"hasPrevious":false,"number":0,"size":20,"totalElements":1,"totalPages":1}
     * results : [{"id":1,"lastUpdateBy":1,"lastUpdateTime":1501220921537,"latitude":111,"longitude":111,"projectId":2,"stationEn":"stationEn0_update","stationName":"广兰路","stationNameShort":"stationNameShort0_update","stationNo":"1","stationNoName":"1广兰路","stationSn":"stationSn0_update"}]
     * timestamp : 1504171841952
     */
    /**
     * hasNext : false
     * hasPrevious : false
     * number : 0
     * size : 20
     * totalElements : 1
     * totalPages : 1
     */

    private PageBean page;
    /**
     * id : 1
     * lastUpdateBy : 1
     * lastUpdateTime : 1501220921537
     * latitude : 111.0
     * longitude : 111.0
     * projectId : 2
     * stationEn : stationEn0_update
     * stationName : 广兰路
     * stationNameShort : stationNameShort0_update
     * stationNo : 1
     * stationNoName : 1广兰路
     * stationSn : stationSn0_update
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
        private Integer id;
        private Integer lastUpdateBy;
        private Long lastUpdateTime;
        private double latitude;
        private double longitude;
        private Integer projectId;
        private String stationEn;
        private String stationName;
        private String stationNameShort;
        private String stationNo;
        private String stationNoName;
        private String stationSn;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getLastUpdateBy() {
            return lastUpdateBy;
        }

        public void setLastUpdateBy(Integer lastUpdateBy) {
            this.lastUpdateBy = lastUpdateBy;
        }

        public Long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public String getStationEn() {
            return stationEn;
        }

        public void setStationEn(String stationEn) {
            this.stationEn = stationEn;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getStationNameShort() {
            return stationNameShort;
        }

        public void setStationNameShort(String stationNameShort) {
            this.stationNameShort = stationNameShort;
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

        public String getStationSn() {
            return stationSn;
        }

        public void setStationSn(String stationSn) {
            this.stationSn = stationSn;
        }
    }
}
