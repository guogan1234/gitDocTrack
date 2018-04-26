package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/8/29.
 * 下一步的操作
 */

public class NextStepbean extends BaseJson{

    /**
     * code : 40000
     * message : 查询成功
     * results : ["flow_report_agree","flow_report_reject"]
     * timestamp : 1503993534167
     */


    private List<String> results;

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
