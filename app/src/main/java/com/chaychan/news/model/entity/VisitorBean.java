package com.chaychan.news.model.entity;

public class VisitorBean {

    /**
     * visitorId : 10000013
     * creationTime : 1543281936000
     * userId : 2018112522392311637
     * userName : 阿拉丁
     * headAvatar : http://pic.qqtn.com/up/2018-2/2018022813173822478.jpg
     */

    private Long visitorId;
    private String creationTime;
    private String userId;//访问者
    private String userName;
    private String headAvatar;

    public Long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadAvatar() {
        return headAvatar;
    }

    public void setHeadAvatar(String headAvatar) {
        this.headAvatar = headAvatar;
    }
}
