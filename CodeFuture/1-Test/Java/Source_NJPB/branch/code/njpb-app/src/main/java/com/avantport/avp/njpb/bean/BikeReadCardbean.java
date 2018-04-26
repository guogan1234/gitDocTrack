package com.avantport.avp.njpb.bean;

/**
 * Created by len on 2017/9/6.
 * 自行车读卡
 */

public class BikeReadCardbean extends BaseJson{


    /**
     * code : 40000
     * message : 查询成功
     * timestamp : 1506594121684
     * result : {"id":1013,"createTime":1505297513762,"createBy":1,"lastUpdateTime":1505297513772,"lastUpdateBy":1,"estateNo":10000001,"estateName":"测试11","stationId":1,"category":0,"estateTypeId":1,"estateSn":"10000070","logicalId":10001030,"projectId":2,"estatePath":"10001030","bikeFrameNo":2090151}
     */
    /**
     * id : 1013
     * createTime : 1505297513762
     * createBy : 1
     * lastUpdateTime : 1505297513772
     * lastUpdateBy : 1
     * estateNo : 10000001
     * estateName : 测试11
     * stationId : 1
     * category : 0
     * estateTypeId : 1
     * estateSn : 10000070
     * logicalId : 10001030
     * projectId : 2
     * estatePath : 10001030
     * bikeFrameNo : 2090151
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private Integer id;
        private Long createTime;
        private Integer createBy;
        private Long lastUpdateTime;
        private Integer lastUpdateBy;
        private Integer estateNo;
        private String estateName;
        private Integer stationId;
        private Integer category;
        private Integer estateTypeId;
        private String estateSn;
        private Integer logicalId;
        private Integer projectId;
        private String estatePath;
        private Integer bikeFrameNo;

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        private  String supplierName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Integer getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Integer createBy) {
            this.createBy = createBy;
        }

        public Long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public Integer getLastUpdateBy() {
            return lastUpdateBy;
        }

        public void setLastUpdateBy(Integer lastUpdateBy) {
            this.lastUpdateBy = lastUpdateBy;
        }

        public Integer getEstateNo() {
            return estateNo;
        }

        public void setEstateNo(Integer estateNo) {
            this.estateNo = estateNo;
        }

        public String getEstateName() {
            return estateName;
        }

        public void setEstateName(String estateName) {
            this.estateName = estateName;
        }

        public Integer getStationId() {
            return stationId;
        }

        public void setStationId(Integer stationId) {
            this.stationId = stationId;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public Integer getEstateTypeId() {
            return estateTypeId;
        }

        public void setEstateTypeId(Integer estateTypeId) {
            this.estateTypeId = estateTypeId;
        }

        public String getEstateSn() {
            return estateSn;
        }

        public void setEstateSn(String estateSn) {
            this.estateSn = estateSn;
        }

        public Integer getLogicalId() {
            return logicalId;
        }

        public void setLogicalId(Integer logicalId) {
            this.logicalId = logicalId;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public String getEstatePath() {
            return estatePath;
        }

        public void setEstatePath(String estatePath) {
            this.estatePath = estatePath;
        }

        public Integer getBikeFrameNo() {
            return bikeFrameNo;
        }

        public void setBikeFrameNo(Integer bikeFrameNo) {
            this.bikeFrameNo = bikeFrameNo;
        }
    }
}
