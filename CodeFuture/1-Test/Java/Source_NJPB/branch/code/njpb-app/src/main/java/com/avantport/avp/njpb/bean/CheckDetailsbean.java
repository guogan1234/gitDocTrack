package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/24.
 */

public class CheckDetailsbean extends BaseJson{


    /**
     * code : 40000
     * message : 查询成功
     * results : [{"applyRemark":"","applyTime":1505982398481,"applyUserId":1,"applyUserName":"管理员","category":2,"corpId":1,"corpName":"总公司","count":5,"createBy":1,"createTime":1505982398481,"estateTypeId":115,"estateTypeName":"挡泥板","id":56,"lastUpdateBy":1,"lastUpdateTime":1505982398505,"partsType":2,"processUserId":1,"processUserName":"管理员","serialNo":"20170921162638171","stockWorkOrderId":36,"stockWorkOrderStatusId":100,"stockWorkOrderTypeId":1,"stockWorkOrderTypeName":"领料"},{"applyRemark":"","applyTime":1505982398481,"applyUserId":1,"applyUserName":"管理员","category":2,"corpId":1,"corpName":"总公司","count":14,"createBy":1,"createTime":1505982398481,"estateTypeId":114,"estateTypeName":"车把套","id":57,"lastUpdateBy":1,"lastUpdateTime":1505982398505,"partsType":2,"processUserId":1,"processUserName":"管理员","serialNo":"20170921162638171","stockWorkOrderId":36,"stockWorkOrderStatusId":100,"stockWorkOrderTypeId":1,"stockWorkOrderTypeName":"领料"}]
     * timestamp : 1505983079153
     */


    /**
     * applyRemark :
     * applyTime : 1505982398481
     * applyUserId : 1
     * applyUserName : 管理员
     * category : 2
     * corpId : 1
     * corpName : 总公司
     * count : 5
     * createBy : 1
     * createTime : 1505982398481
     * estateTypeId : 115
     * estateTypeName : 挡泥板
     * id : 56
     * lastUpdateBy : 1
     * lastUpdateTime : 1505982398505
     * partsType : 2
     * processUserId : 1
     * processUserName : 管理员
     * serialNo : 20170921162638171
     * stockWorkOrderId : 36
     * stockWorkOrderStatusId : 100
     * stockWorkOrderTypeId : 1
     * stockWorkOrderTypeName : 领料
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String applyRemark;
        private long applyTime;
        private int applyUserId;
        private String applyUserName;
        private int category;
        private int corpId;
        private String corpName;
        private int count;
        private int createBy;
        private long createTime;
        private int estateTypeId;
        private String estateTypeName;
        private int id;
        private int lastUpdateBy;
        private long lastUpdateTime;
        private int partsType;
        private int processUserId;
        private String processUserName;
        private String serialNo;
        private int stockWorkOrderId;
        private int stockWorkOrderStatusId;
        private int stockWorkOrderTypeId;
        private String stockWorkOrderTypeName;

        public String getApplyRemark() {
            return applyRemark;
        }

        public void setApplyRemark(String applyRemark) {
            this.applyRemark = applyRemark;
        }

        public long getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(long applyTime) {
            this.applyTime = applyTime;
        }

        public int getApplyUserId() {
            return applyUserId;
        }

        public void setApplyUserId(int applyUserId) {
            this.applyUserId = applyUserId;
        }

        public String getApplyUserName() {
            return applyUserName;
        }

        public void setApplyUserName(String applyUserName) {
            this.applyUserName = applyUserName;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getCorpId() {
            return corpId;
        }

        public void setCorpId(int corpId) {
            this.corpId = corpId;
        }

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getEstateTypeId() {
            return estateTypeId;
        }

        public void setEstateTypeId(int estateTypeId) {
            this.estateTypeId = estateTypeId;
        }

        public String getEstateTypeName() {
            return estateTypeName;
        }

        public void setEstateTypeName(String estateTypeName) {
            this.estateTypeName = estateTypeName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLastUpdateBy() {
            return lastUpdateBy;
        }

        public void setLastUpdateBy(int lastUpdateBy) {
            this.lastUpdateBy = lastUpdateBy;
        }

        public long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public int getPartsType() {
            return partsType;
        }

        public void setPartsType(int partsType) {
            this.partsType = partsType;
        }

        public int getProcessUserId() {
            return processUserId;
        }

        public void setProcessUserId(int processUserId) {
            this.processUserId = processUserId;
        }

        public String getProcessUserName() {
            return processUserName;
        }

        public void setProcessUserName(String processUserName) {
            this.processUserName = processUserName;
        }

        public String getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(String serialNo) {
            this.serialNo = serialNo;
        }

        public int getStockWorkOrderId() {
            return stockWorkOrderId;
        }

        public void setStockWorkOrderId(int stockWorkOrderId) {
            this.stockWorkOrderId = stockWorkOrderId;
        }

        public int getStockWorkOrderStatusId() {
            return stockWorkOrderStatusId;
        }

        public void setStockWorkOrderStatusId(int stockWorkOrderStatusId) {
            this.stockWorkOrderStatusId = stockWorkOrderStatusId;
        }

        public int getStockWorkOrderTypeId() {
            return stockWorkOrderTypeId;
        }

        public void setStockWorkOrderTypeId(int stockWorkOrderTypeId) {
            this.stockWorkOrderTypeId = stockWorkOrderTypeId;
        }

        public String getStockWorkOrderTypeName() {
            return stockWorkOrderTypeName;
        }

        public void setStockWorkOrderTypeName(String stockWorkOrderTypeName) {
            this.stockWorkOrderTypeName = stockWorkOrderTypeName;
        }
    }
}
