package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/9/4.
 */

public class BikeMessage {

    /**
     * code : 40000
     * message : 查询成功
     * results : [{"createTime":1501580986858,"id":1,"lastUpdateTime":1501580986858,"supplierAddr":"南京","supplierName":"捷安特","supplierTel":"12312133131"},{"createTime":1502270519252,"id":3,"lastUpdateTime":1502270519252,"supplierAddr":"天津","supplierName":"富士达","supplierTel":"11111111111"},{"id":2,"lastUpdateBy":1,"lastUpdateTime":1504333788855,"supplierAddr":"南京","supplierName":"艾玛","supplierTel":"188888888887"}]
     * timestamp : 1504508470638
     */

    private Integer code;
    private String message;
    private Long timestamp;
    /**
     * createTime : 1501580986858
     * id : 1
     * lastUpdateTime : 1501580986858
     * supplierAddr : 南京
     * supplierName : 捷安特
     * supplierTel : 12312133131
     */

    private List<ResultsBean> results;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private Long createTime;
        private Integer id;
        private Long lastUpdateTime;
        private String supplierAddr;
        private String supplierName;
        private String supplierTel;

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

        public String getSupplierAddr() {
            return supplierAddr;
        }

        public void setSupplierAddr(String supplierAddr) {
            this.supplierAddr = supplierAddr;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getSupplierTel() {
            return supplierTel;
        }

        public void setSupplierTel(String supplierTel) {
            this.supplierTel = supplierTel;
        }
    }
}
