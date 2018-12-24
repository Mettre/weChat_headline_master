package com.chaychan.news.model.entity;

import java.io.Serializable;

public class UserBean implements Serializable{

    /**
     * publisherUserName : 阿凡达
     * publisherHeadAvatar : string
     * publisherUserId : 2018112209485799882
     */

    private String publisherUserName;
    private String publisherHeadAvatar;
    private String publisherUserId;
    private String publisherBackgroundWall;

    public String getPublisherBackgroundWall() {
        return publisherBackgroundWall;
    }

    public void setPublisherBackgroundWall(String publisherBackgroundWall) {
        this.publisherBackgroundWall = publisherBackgroundWall;
    }

    public String getPublisherUserName() {
        return publisherUserName;
    }

    public void setPublisherUserName(String publisherUserName) {
        this.publisherUserName = publisherUserName;
    }

    public String getPublisherHeadAvatar() {
        return publisherHeadAvatar;
    }

    public void setPublisherHeadAvatar(String publisherHeadAvatar) {
        this.publisherHeadAvatar = publisherHeadAvatar;
    }

    public String getPublisherUserId() {
        return publisherUserId;
    }

    public void setPublisherUserId(String publisherUserId) {
        this.publisherUserId = publisherUserId;
    }
}
