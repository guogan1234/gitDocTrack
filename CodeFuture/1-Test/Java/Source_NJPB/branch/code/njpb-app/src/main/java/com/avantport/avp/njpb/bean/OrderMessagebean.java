package com.avantport.avp.njpb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by len on 2017/8/28.
 */

public class OrderMessagebean extends BaseJson{


    /**
     * code : 40000
     * message : 查询成功
     * page : {"hasNext":false,"hasPrevious":false,"number":0,"size":20,"totalElements":1,"totalPages":1}
     * results : [{"assignEmployee":1,"assignEmployeeUserName":"admin","category":1,"corpName":"一公司","createBy":1,"createTime":1502335102268,"estateId":2,"estateName":"自行车1","estateNo":1,"estateSn":"10000002","estateTypeId":2,"estateTypeName":"自行车","estateTypeNameEn":"bicycle","faultDescription":"自行车出毛病了","id":8,"lastUpdateBy":1,"lastUpdateTime":1502335108475,"level":2,"levelColor":"level_2","processInstanceId":"55001","projectId":2,"repairEmployee":1,"repairEmployeeUserName":"admin","reportEmployee":1,"reportEmployeeUserName":"admin","reportTime":1502335102249,"reportWay":1,"serialNo":"20170810111822672","stationId":1,"stationName":"广兰路","statusId":100,"supplierName":"捷安特","typeId":1,"workOrderStatusNameCn":"工单已创建","workOrderTypeNameCn":"报修维修工单"}]
     * timestamp : 1503908884220
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
     * assignEmployee : 1
     * assignEmployeeUserName : admin
     * category : 1
     * corpName : 一公司
     * createBy : 1
     * createTime : 1502335102268
     * estateId : 2
     * estateName : 自行车1
     * estateNo : 1
     * estateSn : 10000002
     * estateTypeId : 2
     * estateTypeName : 自行车
     * estateTypeNameEn : bicycle
     * faultDescription : 自行车出毛病了
     * id : 8
     * lastUpdateBy : 1
     * lastUpdateTime : 1502335108475
     * level : 2
     * levelColor : level_2
     * processInstanceId : 55001
     * projectId : 2
     * repairEmployee : 1
     * repairEmployeeUserName : admin
     * reportEmployee : 1
     * reportEmployeeUserName : admin
     * reportTime : 1502335102249
     * reportWay : 1
     * serialNo : 20170810111822672
     * stationId : 1
     * stationName : 广兰路
     * statusId : 100
     * supplierName : 捷安特
     * typeId : 1
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

    public static class ResultsBean implements Serializable{
        private Integer assignEmployee;
        private String assignEmployeeUserName;
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
        private Integer id;
        private Integer lastUpdateBy;
        private Long lastUpdateTime;
        private Integer level;
        private String levelColor;
        private String processInstanceId;
        private Integer projectId;
        private Integer repairEmployee;
        private String repairEmployeeUserName;
        private Integer reportEmployee;
        private String reportEmployeeUserName;
        private Long reportTime;
        private Integer reportWay;
        private String serialNo;
        private Integer stationId;
        private String stationName;
        private Integer statusId;
        private String supplierName;
        private Integer typeId;
        private String workOrderStatusNameCn;
        private String workOrderTypeNameCn;
        private Integer bikeFrameNo;

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

        public String getProcessInstanceId() {
            return processInstanceId;
        }

        public void setProcessInstanceId(String processInstanceId) {
            this.processInstanceId = processInstanceId;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
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

