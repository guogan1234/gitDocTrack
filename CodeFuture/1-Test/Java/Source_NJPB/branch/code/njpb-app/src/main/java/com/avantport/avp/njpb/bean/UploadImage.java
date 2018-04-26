package com.avantport.avp.njpb.bean;

import java.util.List;

/**
 * Created by len on 2017/9/8.
 */

public class UploadImage extends BaseJson{

    /**
     * code : 10000
     * message : 新建成功
     * timestamp : 1505185910444
     * results : [8,9,3]
     */


    private List<Integer> results;

    public List<Integer> getResults() {
        return results;
    }

    public void setResults(List<Integer> results) {
        this.results = results;
    }
}
