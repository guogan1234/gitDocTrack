package com.avantport.avp.njpb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by len on 2017/9/1.
 * 设备类型
 */

public class DeviceTypebean extends  BaseJson{

    /**
     * code : 40000
     * message : 查询成功
     * results : [{"category":1,"createTime":1501579044760,"id":1,"lastUpdateTime":1501579044760,"name":"车桩","nameEn":"bicycleStake","partsType":1},{"category":1,"createTime":1501579069906,"id":2,"lastUpdateTime":1501579069906,"name":"自行车","nameEn":"bicycle","partsType":2}]
     * timestamp : 1504245663160
     */


    /**
     * category : 1
     * createTime : 1501579044760
     * id : 1
     * lastUpdateTime : 1501579044760
     * name : 车桩
     * nameEn : bicycleStake
     * partsType : 1
     */


    private List<ResultsBean> results;
    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable{
        private Integer category;
        private Long createTime;
        private Integer id;
        private Long lastUpdateTime;
        private String name;
        private String nameEn;
        private Integer partsType;
        private int count =1;
        private boolean selected;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
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

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public Integer getPartsType() {
            return partsType;
        }

        public void setPartsType(Integer partsType) {
            this.partsType = partsType;
        }
    }
}
