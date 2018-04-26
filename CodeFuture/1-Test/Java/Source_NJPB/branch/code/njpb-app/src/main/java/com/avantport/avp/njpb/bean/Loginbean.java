package com.avantport.avp.njpb.bean;

/**
 * Created by len on 2017/8/25.
 */

public class Loginbean {

    /**
     * access_token : bd933b38-8431-4ef7-9520-82a16b5bd82b
     * token_type : bearer
     * refresh_token : 12ee538d-05a7-4e59-8f0c-7cad8490ab1f
     * expires_in : 21899
     * scope : openid
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
