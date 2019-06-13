package com.chaychan.news.model.entity;

/**
 * 登陆返回
 */
public class LoginBean {
    /**
     * access_token : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDE4MTExNTE0NTU0ODAxNTM5IiwiZXhwIjoxNTYwNjYzMTM3fQ.NvgKE3wBvpef-88Q7MgLf_IhePU5wcrEV9iTJayqbTY
     * token_type : basic
     * expires_in : 4320
     * userId : 2018111514554801539
     */

    private String access_token;
    private String token_type;
    private int expires_in;
    private String userId;

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

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
