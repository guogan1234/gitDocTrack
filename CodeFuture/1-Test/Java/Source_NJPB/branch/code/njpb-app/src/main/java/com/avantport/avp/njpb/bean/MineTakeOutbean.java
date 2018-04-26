package com.avantport.avp.njpb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by len on 2017/8/17.
 * 我的领料
 */

public class MineTakeOutbean extends BaseJson{


    /**
     * code : 40000
     * message : 查询成功
     * page : {"hasNext":false,"hasPrevious":false,"number":0,"size":20,"totalElements":9,"totalPages":1}
     * results : [{"applyRemark":"","applyTime":1505982398481,"applyUserId":1,"applyUserName":"管理员","confirmTime":1502866213487,"corpId":1,"corpName":"总公司","createBy":1,"createTime":1505982398481,"id":36,"lastUpdateBy":1,"lastUpdateTime":1505982398505,"operationResult":0,"processUserId":1,"processUserName":"管理员","rejectRemark":"11212","serialNo":"20170921162638171","stockWorkOrderStatusId":100,"stockWorkOrderTypeId":1},{"applyRemark":"","applyTime":1505982341207,"applyUserId":1,"applyUserName":"管理员","corpId":1,"corpName":"总公司","createBy":1,"createTime":1505982341209,"id":35,"lastUpdateBy":1,"lastUpdateTime":1505982341419,"operationResult":0,"processUserId":1,"processUserName":"管理员","serialNo":"20170921162541841","stockWorkOrderStatusId":100,"stockWorkOrderTypeId":1},{"applyTime":1502951850450,"applyUserId":1,"applyUserName":"管理员","confirmTime":1502866213487,"corpId":1,"corpName":"总公司","createBy":1,"createTime":1502867530174,"id":5,"lastUpdateBy":1,"lastUpdateTime":1503639922923,"operationResult":1,"processUserId":1,"processUserName":"管理员","serialNo":"20170816151150629","stockWorkOrderStatusId":200,"stockWorkOrderTypeId":1},{"applyRemark":"\u201c \u201d","applyTime":1505114115961,"applyUserId":1,"applyUserName":"管理员","confirmTime":1502866213487,"corpId":1,"corpName":"总公司","createBy":1,"createTime":1505114115967,"id":11,"lastUpdateBy":1,"lastUpdateTime":1505987317475,"operationResult":1,"processUserId":1,"processUserName":"管理员","serialNo":"20170911151515109","stockWorkOrderStatusId":200,"stockWorkOrderTypeId":1},{"applyTime":1503371797190,"applyUserId":1,"applyUserName":"管理员","confirmTime":1502866213487,"corpId":1,"corpName":"总公司","createBy":1,"createTime":1503371797214,"id":8,"lastUpdateBy":1,"lastUpdateTime":1503371920786,"operationResult":1,"processUserId":1,"processUserName":"管理员","serialNo":"2017082211163731","stockWorkOrderStatusId":200,"stockWorkOrderTypeId":1},{"applyTime":1503470534571,"applyUserId":1,"applyUserName":"管理员","corpId":1,"corpName":"总公司","createBy":1,"createTime":1503470534579,"id":10,"lastUpdateBy":1,"lastUpdateTime":1503632235399,"operationResult":1,"processUserId":1,"processUserName":"管理员","serialNo":"20170823144214397","stockWorkOrderStatusId":200,"stockWorkOrderTypeId":1},{"applyTime":1502866213487,"applyUserId":1,"applyUserName":"管理员","confirmTime":1502866213487,"corpId":1,"corpName":"总公司","createBy":1,"createTime":1502866213506,"id":4,"lastUpdateBy":1,"lastUpdateTime":1505267012389,"operationResult":0,"processUserId":1,"processUserName":"管理员","rejectRemark":"11212","serialNo":"20170816145013559","stockWorkOrderStatusId":300,"stockWorkOrderTypeId":1},{"applyTime":1503372766691,"applyUserId":1,"applyUserName":"管理员","confirmTime":1502866213487,"corpId":1,"corpName":"总公司","createBy":1,"createTime":1503372766691,"id":9,"lastUpdateBy":1,"lastUpdateTime":1503376394222,"operationResult":1,"processUserId":1,"processUserName":"管理员","serialNo":"20170822113246611","stockWorkOrderStatusId":500,"stockWorkOrderTypeId":1},{"applyTime":1502867763707,"applyUserId":1,"applyUserName":"管理员","confirmTime":1502866213487,"corpId":1,"corpName":"总公司","createBy":1,"createTime":1502867763724,"id":6,"lastUpdateBy":1,"lastUpdateTime":1502952485149,"operationResult":1,"processUserId":1,"processUserName":"管理员","serialNo":"20170816151603573","stockWorkOrderStatusId":500,"stockWorkOrderTypeId":1}]
     * timestamp : 1506501086723
     */


    /**
     * hasNext : false
     * hasPrevious : false
     * number : 0
     * size : 20
     * totalElements : 9
     * totalPages : 1
     */

    private PageBean page;
    /**
     * applyRemark :
     * applyTime : 1505982398481
     * applyUserId : 1
     * applyUserName : 管理员
     * confirmTime : 1502866213487
     * corpId : 1
     * corpName : 总公司
     * createBy : 1
     * createTime : 1505982398481
     * id : 36
     * lastUpdateBy : 1
     * lastUpdateTime : 1505982398505
     * operationResult : 0
     * processUserId : 1
     * processUserName : 管理员
     * rejectRemark : 11212
     * serialNo : 20170921162638171
     * stockWorkOrderStatusId : 100
     * stockWorkOrderTypeId : 1
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
        private String applyRemark;
        private Long applyTime;
        private Integer applyUserId;
        private String applyUserName;
        private Long confirmTime;
        private Integer corpId;
        private String corpName;
        private Integer createBy;
        private Long createTime;
        private Integer id;
        private Integer lastUpdateBy;
        private Long lastUpdateTime;
        private Integer operationResult;
        private Integer processUserId;
        private String processUserName;
        private String rejectRemark;
        private String serialNo;
        private Integer stockWorkOrderStatusId;
        private Integer stockWorkOrderTypeId;
        private String stockWorkOrderStatusNameCn;//物料审核的状态

        public String getStockWorkOrderStatusNameCn() {
            return stockWorkOrderStatusNameCn;
        }

        public void setStockWorkOrderStatusNameCn(String stockWorkOrderStatusNameCn) {
            this.stockWorkOrderStatusNameCn = stockWorkOrderStatusNameCn;
        }

        public String getApplyRemark() {
            return applyRemark;
        }

        public void setApplyRemark(String applyRemark) {
            this.applyRemark = applyRemark;
        }

        public Long getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(Long applyTime) {
            this.applyTime = applyTime;
        }

        public Integer getApplyUserId() {
            return applyUserId;
        }

        public void setApplyUserId(Integer applyUserId) {
            this.applyUserId = applyUserId;
        }

        public String getApplyUserName() {
            return applyUserName;
        }

        public void setApplyUserName(String applyUserName) {
            this.applyUserName = applyUserName;
        }

        public Long getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(Long confirmTime) {
            this.confirmTime = confirmTime;
        }

        public Integer getCorpId() {
            return corpId;
        }

        public void setCorpId(Integer corpId) {
            this.corpId = corpId;
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

        public Integer getOperationResult() {
            return operationResult;
        }

        public void setOperationResult(Integer operationResult) {
            this.operationResult = operationResult;
        }

        public Integer getProcessUserId() {
            return processUserId;
        }

        public void setProcessUserId(Integer processUserId) {
            this.processUserId = processUserId;
        }

        public String getProcessUserName() {
            return processUserName;
        }

        public void setProcessUserName(String processUserName) {
            this.processUserName = processUserName;
        }

        public String getRejectRemark() {
            return rejectRemark;
        }

        public void setRejectRemark(String rejectRemark) {
            this.rejectRemark = rejectRemark;
        }

        public String getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(String serialNo) {
            this.serialNo = serialNo;
        }

        public Integer getStockWorkOrderStatusId() {
            return stockWorkOrderStatusId;
        }

        public void setStockWorkOrderStatusId(Integer stockWorkOrderStatusId) {
            this.stockWorkOrderStatusId = stockWorkOrderStatusId;
        }

        public Integer getStockWorkOrderTypeId() {
            return stockWorkOrderTypeId;
        }

        public void setStockWorkOrderTypeId(Integer stockWorkOrderTypeId) {
            this.stockWorkOrderTypeId = stockWorkOrderTypeId;
        }
    }
}
