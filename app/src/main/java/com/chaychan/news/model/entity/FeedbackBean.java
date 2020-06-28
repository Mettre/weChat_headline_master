package com.chaychan.news.model.entity;

/**
 * 我的反馈
 */
public class FeedbackBean {

    /**
     * feedbackId : 1001
     * userId : 2018111514554801539
     * creationTime : 1543563704000
     * updateTime : 1543564817000
     * content : 你们这个app好难用啊
     * state : ADOPTED
     * userName : 安德拉
     * headAvatar : http://img4.duitang.com/uploads/item/201407/16/20140716132526_TcyTY.thumb.600_0.jpeg
     * phone : 18844157372
     */

    private int feedbackId;
    private String userId;
    private String creationTime;
    private String updateTime;
    private String content;
    private String state;
    private String userName;
    private String headAvatar;
    private String phone;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
