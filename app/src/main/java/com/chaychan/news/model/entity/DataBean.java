package com.chaychan.news.model.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class DataBean extends DataSupport implements Serializable {
    /**
     * followId : 100002
     * headAvatar : http://img0.imgtn.bdimg.com/it/u=2577022489,1269768065&fm=26&gp=0.jpg
     * myUserId : 2018111514554801539
     * updateTime : 2018-11-23 22:56:13
     * userId : 2018112209485799882
     * userName : 阿凡达
     */

    private int followId;
    private String headAvatar;
    private String myUserId;
    private String updateTime;
    private String userId;
    private String userName;

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public String getHeadAvatar() {
        return headAvatar;
    }

    public void setHeadAvatar(String headAvatar) {
        this.headAvatar = headAvatar;
    }

    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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
}
