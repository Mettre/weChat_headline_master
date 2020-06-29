package com.chaychan.news.model.entity;

public class RecommendeBean {

    private String recommendId;
    private String userId;
    private String userName;
    private String headAvatar;
    private Boolean hasFollow;

    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
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

    public Boolean getHasFollow() {
        return hasFollow;
    }

    public void setHasFollow(Boolean hasFollow) {
        this.hasFollow = hasFollow;
    }
}
