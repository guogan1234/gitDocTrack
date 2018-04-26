package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/31.
 */

public class CompanyInfobean {

    /**
     * code : 40000
     * message : 查询成功
     * results : [{"corpAddr":"test","corpContactName":"test","corpContactTel":"123121313132","corpLevel":0,"corpName":"总公司","corpNo":0,"createTime":1500966405203,"id":1,"lastUpdateTime":1500966405203},{"corpAddr":"test","corpContactName":"test","corpContactTel":"123121313132","corpLevel":1,"corpName":"一公司","corpNo":1,"createTime":1500966428773,"id":2,"lastUpdateTime":1500966428773},{"corpAddr":"南京","corpContactName":"xiaozhou","corpContactTel":"13327992293","corpLevel":1,"corpName":"二公司","corpNo":2,"createTime":1501495901279,"id":3,"lastUpdateTime":1501495901279},{"corpAddr":"南京","corpContactName":"azhou","corpContactTel":"13346456293","corpLevel":1,"corpName":"三公司","corpNo":3,"createTime":1501495932210,"id":4,"lastUpdateTime":1501495932210},{"corpAddr":"南京","corpName":"四分公司","corpNo":4,"createTime":1502270734500,"id":5,"lastUpdateTime":1502270734500}]
     * timestamp : 1504168568079
     */

    /**
     * corpAddr : test
     * corpContactName : test
     * corpContactTel : 123121313132
     * corpLevel : 0
     * corpName : 总公司
     * corpNo : 0
     * createTime : 1500966405203
     * id : 1
     * lastUpdateTime : 1500966405203
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String corpAddr;
        private String corpContactName;
        private String corpContactTel;
        private Integer corpLevel;
        private String corpName;
        private Integer corpNo;
        private Long createTime;
        private Integer id;
        private Long lastUpdateTime;

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

        public Integer getCorpLevel() {
            return corpLevel;
        }

        public void setCorpLevel(Integer corpLevel) {
            this.corpLevel = corpLevel;
        }

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public Integer getCorpNo() {
            return corpNo;
        }

        public void setCorpNo(Integer corpNo) {
            this.corpNo = corpNo;
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
    }
}
