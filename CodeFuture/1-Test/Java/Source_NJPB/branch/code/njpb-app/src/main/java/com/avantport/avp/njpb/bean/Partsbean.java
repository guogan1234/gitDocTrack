package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/29.
 * 模块类型，部件
 */

public class Partsbean {

    /**
     * code : 40000
     * message : 查询成功
     * results : [{"category":2,"createTime":1501579480945,"id":5,"lastUpdateTime":1501579480945,"name":"车轮","nameEn":"bicycleWheel","partsType":2}]
     * timestamp : 1503986882870
     */

    private Integer code;
    private String message;
    private Long timestamp;
    /**
     * category : 2
     * createTime : 1501579480945
     * id : 5
     * lastUpdateTime : 1501579480945
     * name : 车轮
     * nameEn : bicycleWheel
     * partsType : 2
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
        private Integer category;
        private Long createTime;
        private Integer id;
        private Long lastUpdateTime;
        private String name;
        private String nameEn;
        private Integer partsType;
        private boolean selected;//增加的字段

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
