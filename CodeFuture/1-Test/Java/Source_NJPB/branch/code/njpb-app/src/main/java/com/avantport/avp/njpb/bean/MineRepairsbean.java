package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/21.
 */

public class MineRepairsbean extends BaseJson{

    /**
     * code : 40000
     * message : 查询成功
     * page : {"hasNext":false,"hasPrevious":false,"number":0,"size":20,"totalElements":3,"totalPages":1}
     * results : [{"assignEmployee":39,"assignEmployeeUserName":1212,"assignRemark":12121,"assignTime":121221212,"backRemark":12121,"category":1,"corpName":"一分公司","createBy":1,"createTime":1504511986574,"estateId":1,"estateName":"车桩","estateNo":12,"estateSn":"10000001","estateTypeId":1,"estateTypeName":"车桩","estateTypeNameEn":"bicycleStake","faultDescription":"","fixed":1212,"id":81,"lastUpdateBy":1,"lastUpdateTime":1504511986594,"latitude":1212,"level":1,"levelColor":"","longitude":121212,"maintainRemark":12121,"processInstanceId":"185176","projectId":2,"removeTime":122122,"repairConfirmTime":1212,"repairEmployee":121212,"repairEmployeeUserName":2121,"repairEndTime":1212,"repairOverTime":12121,"repairRemark":121221,"repairStartTime":11212,"repairTimeOutDate":1221,"reponseOverTime":121212,"reportEmployee":1,"reportEmployeeUserName":"管理员","reportTime":1504511986574,"reportWay":1,"responseTimeOutDate":1221,"serialNo":"20170904155946989","stationId":2,"stationName":"金科路","statusId":100,"supplierName":"捷安特","typeId":1,"workOrderSource":121212,"workOrderStatusNameCn":"工单已创建","workOrderTypeNameCn":"报修维修工单"},{"assignEmployee":31,"category":1,"corpName":"一分公司","createBy":1,"createTime":1504510903969,"estateId":1003,"estateName":"自行车5","estateSn":"11111111111111","estateTypeId":2,"estateTypeName":"自行车","estateTypeNameEn":"bicycle","faultDescription":"","id":80,"lastUpdateBy":1,"lastUpdateTime":1504510903993,"levelColor":"","processInstanceId":"185169","projectId":2,"reportEmployee":1,"reportEmployeeUserName":"管理员","reportTime":1504510903969,"reportWay":1,"serialNo":"20170904154143525","stationId":2,"stationName":"金科路","statusId":100,"typeId":1,"workOrderStatusNameCn":"工单已创建","workOrderTypeNameCn":"报修维修工单"},{"assignEmployee":31,"category":1,"corpName":"一分公司","createBy":1,"createTime":1504510524518,"estateId":1003,"estateName":"自行车5","estateSn":"11111111111111","estateTypeId":2,"estateTypeName":"自行车","estateTypeNameEn":"bicycle","faultDescription":"2017年9月4日15:35:29","id":79,"lastUpdateBy":1,"lastUpdateTime":1504510524545,"levelColor":"","processInstanceId":"185162","projectId":2,"reportEmployee":1,"reportEmployeeUserName":"管理员","reportTime":1504510524518,"reportWay":1,"serialNo":"20170904153524579","stationId":2,"stationName":"金科路","statusId":100,"typeId":1,"workOrderStatusNameCn":"工单已创建","workOrderTypeNameCn":"报修维修工单"}]
     * timestamp : 1505442990237
     */


    /**
     * hasNext : false
     * hasPrevious : false
     * number : 0
     * size : 20
     * totalElements : 3
     * totalPages : 1
     */

    private PageBean page;
    /**
     * assignEmployee : 39
     * assignEmployeeUserName : 1212
     * assignRemark : 12121
     * assignTime : 121221212
     * backRemark : 12121
     * category : 1
     * corpName : 一分公司
     * createBy : 1
     * createTime : 1504511986574
     * estateId : 1
     * estateName : 车桩
     * estateNo : 12
     * estateSn : 10000001
     * estateTypeId : 1
     * estateTypeName : 车桩
     * estateTypeNameEn : bicycleStake
     * faultDescription :
     * fixed : 1212
     * id : 81
     * lastUpdateBy : 1
     * lastUpdateTime : 1504511986594
     * latitude : 1212
     * level : 1
     * levelColor :
     * longitude : 121212
     * maintainRemark : 12121
     * processInstanceId : 185176
     * projectId : 2
     * removeTime : 122122
     * repairConfirmTime : 1212
     * repairEmployee : 121212
     * repairEmployeeUserName : 2121
     * repairEndTime : 1212
     * repairOverTime : 12121
     * repairRemark : 121221
     * repairStartTime : 11212
     * repairTimeOutDate : 1221
     * reponseOverTime : 121212
     * reportEmployee : 1
     * reportEmployeeUserName : 管理员
     * reportTime : 1504511986574
     * reportWay : 1
     * responseTimeOutDate : 1221
     * serialNo : 20170904155946989
     * stationId : 2
     * stationName : 金科路
     * statusId : 100
     * supplierName : 捷安特
     * typeId : 1
     * workOrderSource : 121212
     * workOrderStatusNameCn : 工单已创建
     * workOrderTypeNameCn : 报修维修工单
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
        private Integer assignEmployee;
        private String assignEmployeeUserName;
        private String assignRemark;
        private Long assignTime;
        private String backRemark;
        private Integer category;
        private String corpName;
        private Integer createBy;
        private Long createTime;
        private Integer estateId;
        private String estateName;
        private Integer estateNo;
        private String estateSn;
        private Integer estateTypeId;
        private String estateTypeName;
        private String estateTypeNameEn;
        private String faultDescription;
        private String fixed;
        private Integer id;
        private int lastUpdateBy;
        private Long lastUpdateTime;
        private Double latitude;
        private Integer level;
        private String levelColor;
        private Double longitude;
        private String maintainRemark;
        private Integer processInstanceId;
        private Integer projectId;
        private Long repairConfirmTime;
        private Integer repairEmployee;
        private String repairEmployeeUserName;
        private Long repairEndTime;
        private Long repairOverTime;
        private String repairRemark;
        private Long repairStartTime;
        private Long repairTimeOutDate;
        private Long reponseOverTime;
        private Integer reportEmployee;
        private String reportEmployeeUserName;
        private Long reportTime;
        private Integer reportWay;
        private Long responseTimeOutDate;
        private String serialNo;
        private Integer stationId;
        private String stationName;
        private Integer statusId;
        private String supplierName;
        private Integer typeId;
        private int workOrderSource;
        private String workOrderStatusNameCn;
        private String workOrderTypeNameCn;
        private Integer bikeFrameNo;//车架号

        public Integer getBikeFrameNo() {
            return bikeFrameNo;
        }

        public void setBikeFrameNo(Integer bikeFrameNo) {
            this.bikeFrameNo = bikeFrameNo;
        }

        public Integer getAssignEmployee() {
            return assignEmployee;
        }

        public void setAssignEmployee(Integer assignEmployee) {
            this.assignEmployee = assignEmployee;
        }

        public String getAssignEmployeeUserName() {
            return assignEmployeeUserName;
        }

        public void setAssignEmployeeUserName(String assignEmployeeUserName) {
            this.assignEmployeeUserName = assignEmployeeUserName;
        }

        public String getAssignRemark() {
            return assignRemark;
        }

        public void setAssignRemark(String assignRemark) {
            this.assignRemark = assignRemark;
        }

        public Long getAssignTime() {
            return assignTime;
        }

        public void setAssignTime(Long assignTime) {
            this.assignTime = assignTime;
        }

        public String getBackRemark() {
            return backRemark;
        }

        public void setBackRemark(String backRemark) {
            this.backRemark = backRemark;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
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

        public Integer getEstateTypeId() {
            return estateTypeId;
        }

        public void setEstateTypeId(Integer estateTypeId) {
            this.estateTypeId = estateTypeId;
        }

        public String getEstateTypeName() {
            return estateTypeName;
        }

        public void setEstateTypeName(String estateTypeName) {
            this.estateTypeName = estateTypeName;
        }

        public String getEstateTypeNameEn() {
            return estateTypeNameEn;
        }

        public void setEstateTypeNameEn(String estateTypeNameEn) {
            this.estateTypeNameEn = estateTypeNameEn;
        }

        public String getFaultDescription() {
            return faultDescription;
        }

        public void setFaultDescription(String faultDescription) {
            this.faultDescription = faultDescription;
        }

        public String getFixed() {
            return fixed;
        }

        public void setFixed(String fixed) {
            this.fixed = fixed;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public int getLastUpdateBy() {
            return lastUpdateBy;
        }

        public void setLastUpdateBy(int lastUpdateBy) {
            this.lastUpdateBy = lastUpdateBy;
        }

        public Long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getLevelColor() {
            return levelColor;
        }

        public void setLevelColor(String levelColor) {
            this.levelColor = levelColor;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getMaintainRemark() {
            return maintainRemark;
        }

        public void setMaintainRemark(String maintainRemark) {
            this.maintainRemark = maintainRemark;
        }

        public Integer getProcessInstanceId() {
            return processInstanceId;
        }

        public void setProcessInstanceId(Integer processInstanceId) {
            this.processInstanceId = processInstanceId;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public Long getRepairConfirmTime() {
            return repairConfirmTime;
        }

        public void setRepairConfirmTime(Long repairConfirmTime) {
            this.repairConfirmTime = repairConfirmTime;
        }

        public Integer getRepairEmployee() {
            return repairEmployee;
        }

        public void setRepairEmployee(Integer repairEmployee) {
            this.repairEmployee = repairEmployee;
        }

        public String getRepairEmployeeUserName() {
            return repairEmployeeUserName;
        }

        public void setRepairEmployeeUserName(String repairEmployeeUserName) {
            this.repairEmployeeUserName = repairEmployeeUserName;
        }

        public Long getRepairEndTime() {
            return repairEndTime;
        }

        public void setRepairEndTime(Long repairEndTime) {
            this.repairEndTime = repairEndTime;
        }

        public Long getRepairOverTime() {
            return repairOverTime;
        }

        public void setRepairOverTime(Long repairOverTime) {
            this.repairOverTime = repairOverTime;
        }

        public String getRepairRemark() {
            return repairRemark;
        }

        public void setRepairRemark(String repairRemark) {
            this.repairRemark = repairRemark;
        }

        public Long getRepairStartTime() {
            return repairStartTime;
        }

        public void setRepairStartTime(Long repairStartTime) {
            this.repairStartTime = repairStartTime;
        }

        public Long getRepairTimeOutDate() {
            return repairTimeOutDate;
        }

        public void setRepairTimeOutDate(Long repairTimeOutDate) {
            this.repairTimeOutDate = repairTimeOutDate;
        }

        public Long getReponseOverTime() {
            return reponseOverTime;
        }

        public void setReponseOverTime(Long reponseOverTime) {
            this.reponseOverTime = reponseOverTime;
        }

        public Integer getReportEmployee() {
            return reportEmployee;
        }

        public void setReportEmployee(Integer reportEmployee) {
            this.reportEmployee = reportEmployee;
        }

        public String getReportEmployeeUserName() {
            return reportEmployeeUserName;
        }

        public void setReportEmployeeUserName(String reportEmployeeUserName) {
            this.reportEmployeeUserName = reportEmployeeUserName;
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

        public Long getResponseTimeOutDate() {
            return responseTimeOutDate;
        }

        public void setResponseTimeOutDate(Long responseTimeOutDate) {
            this.responseTimeOutDate = responseTimeOutDate;
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

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public Integer getStatusId() {
            return statusId;
        }

        public void setStatusId(Integer statusId) {
            this.statusId = statusId;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public Integer getTypeId() {
            return typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }

        public int getWorkOrderSource() {
            return workOrderSource;
        }

        public void setWorkOrderSource(int workOrderSource) {
            this.workOrderSource = workOrderSource;
        }

        public String getWorkOrderStatusNameCn() {
            return workOrderStatusNameCn;
        }

        public void setWorkOrderStatusNameCn(String workOrderStatusNameCn) {
            this.workOrderStatusNameCn = workOrderStatusNameCn;
        }

        public String getWorkOrderTypeNameCn() {
            return workOrderTypeNameCn;
        }

        public void setWorkOrderTypeNameCn(String workOrderTypeNameCn) {
            this.workOrderTypeNameCn = workOrderTypeNameCn;
        }
    }
}
