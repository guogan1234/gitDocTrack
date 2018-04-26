package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/14.
 */

public class Companybean {

    /**
     * code : 40000
     * message : 查询成功
     * timestamp : 1502686278761
     * results : [{"id":1,"createTime":1500966405203,"lastUpdateTime":1500966405203,"corpName":"总公司","corpNo":0,"corpAddr":"test","corpContactName":"test","corpContactTel":"123121313132","corpLevel":0},{"id":2,"createTime":1500966428773,"lastUpdateTime":1500966428773,"corpName":"一公司","corpNo":1,"corpAddr":"test","corpContactName":"test","corpContactTel":"123121313132","corpLevel":1},{"id":3,"createTime":1501495901279,"lastUpdateTime":1501495901279,"corpName":"二公司","corpNo":2,"corpAddr":"南京","corpContactName":"xiaozhou","corpContactTel":"13327992293","corpLevel":1},{"id":4,"createTime":1501495932210,"lastUpdateTime":1501495932210,"corpName":"三公司","corpNo":3,"corpAddr":"南京","corpContactName":"azhou","corpContactTel":"13346456293","corpLevel":1},{"id":5,"createTime":1502270734500,"lastUpdateTime":1502270734500,"corpName":"四分公司","corpNo":4,"corpAddr":"南京"}]
     */

    private int code;
    private String message;
    private long timestamp;
    /**
     * id : 1
     * createTime : 1500966405203
     * lastUpdateTime : 1500966405203
     * corpName : 总公司
     * corpNo : 0
     * corpAddr : test
     * corpContactName : test
     * corpContactTel : 123121313132
     * corpLevel : 0
     */

    private List<ResultsBean> results;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private int id;
        private long createTime;
        private long lastUpdateTime;
        private String corpName;
        private int corpNo;
        private String corpAddr;
        private String corpContactName;
        private String corpContactTel;
        private int corpLevel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(long lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public int getCorpNo() {
            return corpNo;
        }

        public void setCorpNo(int corpNo) {
            this.corpNo = corpNo;
        }

        public String getCorpAddr() {
            return corpAddr;
        }

        public void setCorpAddr(String corpAddr) {
            this.corpAddr = corpAddr;
        }

        public String getCorpContactName() {
            return corpContactName;
        }

        public void setCorpContactName(String corpContactName) {
            this.corpContactName = corpContactName;
        }

        public String getCorpContactTel() {
            return corpContactTel;
        }

        public void setCorpContactTel(String corpContactTel) {
            this.corpContactTel = corpContactTel;
        }

        public int getCorpLevel() {
            return corpLevel;
        }

        public void setCorpLevel(int corpLevel) {
            this.corpLevel = corpLevel;
        }
    }
}
