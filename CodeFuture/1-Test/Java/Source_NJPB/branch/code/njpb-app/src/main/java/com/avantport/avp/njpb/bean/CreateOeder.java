package com.avantport.avp.njpb.bean;

/**
 * Created by len on 2017/9/2.
 * 创建工单
 */

public class CreateOeder extends BaseJson{


    /**
     * code : 10000
     * message : 新建成功
     * result : {"assignEmployee":15,"createBy":1,"createTime":1504502057803,"estateId":1,"faultDescription":"nihao a ","id":71,"lastUpdateBy":1,"lastUpdateTime":1504502057825,"processInstanceId":"185106","reportEmployee":1,"reportTime":1504502057803,"reportWay":1,"serialNo":"20170904131417180","stationId":2,"statusId":100,"typeId":1}
     * timestamp : 1504502057825
     */


    /**
     * assignEmployee : 15
     * createBy : 1
     * createTime : 1504502057803
     * estateId : 1
     * faultDescription : nihao a
     * id : 71
     * lastUpdateBy : 1
     * lastUpdateTime : 1504502057825
     * processInstanceId : 185106
     * reportEmployee : 1
     * reportTime : 1504502057803
     * reportWay : 1
     * serialNo : 20170904131417180
     * stationId : 2
     * statusId : 100
     * typeId : 1
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private Integer assignEmployee;
        private Integer createBy;
        private Long createTime;
        private Integer estateId;
        private String faultDescription;
        private Integer id;
        private Integer lastUpdateBy;
        private Long lastUpdateTime;
        private String processInstanceId;
        private Integer reportEmployee;
        private Long reportTime;
        private Integer reportWay;
        private String serialNo;
        private Integer stationId;
        private Integer statusId;
        private Integer typeId;

        public Integer getAssignEmployee() {
            return assignEmployee;
        }

        public void setAssignEmployee(Integer assignEmployee) {
            this.assignEmployee = assignEmployee;
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

        public Integer getEstateId() {
            return estateId;
        }

        public void setEstateId(Integer estateId) {
            this.estateId = estateId;
        }

        public String getFaultDescription() {
            return faultDescription;
        }

        public void setFaultDescription(String faultDescription) {
            this.faultDescription = faultDescription;
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

        public String getProcessInstanceId() {
            return processInstanceId;
        }

        public void setProcessInstanceId(String processInstanceId) {
            this.processInstanceId = processInstanceId;
        }

        public Integer getReportEmployee() {
            return reportEmployee;
        }

        public void setReportEmployee(Integer reportEmployee) {
            this.reportEmployee = reportEmployee;
        }

        public Long getReportTime() {
            return reportTime;
        }

        public void setReportTime(Long reportTime) {
            this.reportTime = reportTime;
        }

        public Integer getReportWay() {
            return reportWay;
        }

        public void setReportWay(Integer reportWay) {
            this.reportWay = reportWay;
        }

        public String getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(String serialNo) {
            this.serialNo = serialNo;
        }

        public Integer getStationId() {
            return stationId;
        }

        public void setStationId(Integer stationId) {
            this.stationId = stationId;
        }

        public Integer getStatusId() {
            return statusId;
        }

        public void setStatusId(Integer statusId) {
            this.statusId = statusId;
        }

        public Integer getTypeId() {
            return typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }
    }
}
