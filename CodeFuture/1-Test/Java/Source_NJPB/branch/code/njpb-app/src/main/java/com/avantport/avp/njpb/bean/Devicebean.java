package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/9/1.
 */

public class Devicebean extends BaseJson{

    /**
     * code : 40000
     * message : 查询成功
     * results : [{"category":1,"createBy":7,"createTime":1501580761017,"estateName":"车桩","estateSn":"10000001","estateStatusId":1,"estateTypeId":1,"id":1,"lastUpdateBy":1,"lastUpdateTime":1501656421595,"projectId":2,"stationId":4,"supplierId":1}]
     * timestamp : 1504254135874
     */
    /**
     * category : 1
     * createBy : 7
     * createTime : 1501580761017
     * estateName : 车桩
     * estateSn : 10000001
     * estateStatusId : 1
     * estateTypeId : 1
     * id : 1
     * lastUpdateBy : 1
     * lastUpdateTime : 1501656421595
     * projectId : 2
     * stationId : 4
     * supplierId : 1
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private Integer category;
        private Integer createBy;
        private Long createTime;
        private String estateName;
        private String estateSn;
        private Integer estateStatusId;
        private Integer estateTypeId;
        private Integer id;
        private Integer lastUpdateBy;
        private Long lastUpdateTime;
        private Integer projectId;
        private Integer stationId;
        private Integer supplierId;

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
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

        public Integer getEstateTypeId() {
            return estateTypeId;
        }

        public void setEstateTypeId(Integer estateTypeId) {
            this.estateTypeId = estateTypeId;
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

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public Integer getStationId() {
            return stationId;
        }

        public void setStationId(Integer stationId) {
            this.stationId = stationId;
        }

        public Integer getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(Integer supplierId) {
            this.supplierId = supplierId;
        }
    }
}
