package com.chaychan.news.model.entity;

public class UserHeadInfo {

    private String userName;

    private String headAvatar;

    private String backGroundWall;

    private String UserId;

    public String getBackGroundWall() {
        return backGroundWall;
    }

    public void setBackGroundWall(String backGroundWall) {
        this.backGroundWall = backGroundWall;
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

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
