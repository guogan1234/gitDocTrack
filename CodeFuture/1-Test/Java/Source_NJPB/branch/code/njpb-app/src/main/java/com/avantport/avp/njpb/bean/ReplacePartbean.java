package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/29.
 * 更换的配件信息
 */

public class ReplacePartbean extends BaseJson{


    /**
     * code : 40000
     * message : 查询成功
     * results : [{"createBy":1,"createByUserName":"admin","createTime":1502792736951,"estateTypeId":1,"id":1,"lastUpdateBy":1,"lastUpdateByUserName":"admin","lastUpdateTime":1502792736951,"name":"车桩","replaceCount":1,"workOrderId":1}]
     * timestamp : 1504084201326
     */
    /**
     * createBy : 1
     * createByUserName : admin
     * createTime : 1502792736951
     * estateTypeId : 1
     * id : 1
     * lastUpdateBy : 1
     * lastUpdateByUserName : admin
     * lastUpdateTime : 1502792736951
     * name : 车桩
     * replaceCount : 1
     * workOrderId : 1
     */

    private List<ResultsBean> results;
    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private Integer createBy;
        private String createByUserName;
        private Long createTime;
        private Integer estateTypeId;
        private Integer id;
        private Integer lastUpdateBy;
        private String lastUpdateByUserName;
        private Long lastUpdateTime;
        private String name;
        private boolean selected;//增加的字段

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        private Integer replaceCount;
        private Integer workOrderId;

        public Integer getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Integer createBy) {
            this.createBy = createBy;
        }

        public String getCreateByUserName() {
            return createByUserName;
        }

        public void setCreateByUserName(String createByUserName) {
            this.createByUserName = createByUserName;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
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

        public String getLastUpdateByUserName() {
            return lastUpdateByUserName;
        }

        public void setLastUpdateByUserName(String lastUpdateByUserName) {
            this.lastUpdateByUserName = lastUpdateByUserName;
        }

        public Long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getReplaceCount() {
            return replaceCount;
        }

        public void setReplaceCount(Integer replaceCount) {
            this.replaceCount = replaceCount;
        }

        public Integer getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(Integer workOrderId) {
            this.workOrderId = workOrderId;
        }
    }
}
