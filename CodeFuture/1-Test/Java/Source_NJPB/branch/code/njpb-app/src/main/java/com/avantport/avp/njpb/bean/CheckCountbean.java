package com.avantport.avp.njpb.bean;

/**
 * Created by len on 2017/8/23.
 * 盘点
 */

public class CheckCountbean extends BaseJson{

    /**
     * code : 0
     * message : 成功
     * result : {"corpCheckLast":0,"corpCheckNow":44,"personCheckLast":0,"personCheckNow":44}
     * timestamp : 1503458534208
     */
    /**
     * corpCheckLast : 0
     * corpCheckNow : 44
     * personCheckLast : 0
     * personCheckNow : 44
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private Integer corpCheckLast;
        private Integer corpCheckNow;
        private Integer personCheckLast;
        private Integer personCheckNow;

        public Integer getCorpCheckLast() {
            return corpCheckLast;
        }

        public void setCorpCheckLast(Integer corpCheckLast) {
            this.corpCheckLast = corpCheckLast;
        }

        public Integer getCorpCheckNow() {
            return corpCheckNow;
        }

        public void setCorpCheckNow(Integer corpCheckNow) {
            this.corpCheckNow = corpCheckNow;
        }

        public Integer getPersonCheckLast() {
            return personCheckLast;
        }

        public void setPersonCheckLast(Integer personCheckLast) {
            this.personCheckLast = personCheckLast;
        }

        public Integer getPersonCheckNow() {
            return personCheckNow;
        }

        public void setPersonCheckNow(Integer personCheckNow) {
            this.personCheckNow = personCheckNow;
        }
    }
}
