package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/18.
 * 我的库存
 */

public class MineStockbean extends BaseJson{

    /**
     * code : 0
     * message : 成功
     * results : [{"category":1,"count":3,"createBy":1,"createTime":1502869618720,"estateTypeId":2,"estateTypeName":"自行车","id":2,"nameEn":"bicycle","partsType":2,"userId":1,"userName":"admin"}]
     * timestamp : 1503051828224
     */


    /**
     * category : 1
     * count : 3
     * createBy : 1
     * createTime : 1502869618720
     * estateTypeId : 2
     * estateTypeName : 自行车
     * id : 2
     * nameEn : bicycle
     * partsType : 2
     * userId : 1
     * userName : admin
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private int category;
        private int count;
        private int createBy;
        private long createTime;
        private int estateTypeId;
        private String estateTypeName;
        private int id;
        private String nameEn;
        private int partsType;
        private int userId;
        private String userName;

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
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

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public int getPartsType() {
            return partsType;
        }

        public void setPartsType(int partsType) {
            this.partsType = partsType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
