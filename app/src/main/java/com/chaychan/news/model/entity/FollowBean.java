package com.chaychan.news.model.entity;

public class FollowBean {

    /**
     * followId : 100003
     * followedUser : 2018112522392311637
     * updateTime : 1543156848000
     * followedUserName : 阿拉丁
     * eachOther : true
     */

    private int followId;
    private String followedUser;
    private long updateTime;
    private String followedUserName;
    private String headAvatar;
    private boolean eachOther;

    public String getHeadAvatar() {
        return headAvatar;
    }

    public void setHeadAvatar(String headAvatar) {
        this.headAvatar = headAvatar;
    }

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public String getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(String followedUser) {
        this.followedUser = followedUser;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getFollowedUserName() {
        return followedUserName;
    }

    public void setFollowedUserName(String followedUserName) {
        this.followedUserName = followedUserName;
    }

    public boolean isEachOther() {
        return eachOther;
    }

    public void setEachOther(boolean eachOther) {
        this.eachOther = eachOther;
    }
}
