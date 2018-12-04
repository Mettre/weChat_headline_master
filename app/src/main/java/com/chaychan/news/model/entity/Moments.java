package com.chaychan.news.model.entity;

import com.chaychan.news.enum_.MomentsEnum;

import java.util.Date;

public class Moments {

    private String momentsId;

    private String momentsTitle;

    private String publisherUserId;

    private Date creationTime;

    private String momentsImage;

    private String publisherUserName;

    private String publisherHeadAvatar;

    private MomentsEnum momentsEnum;

    public MomentsEnum getMomentsEnum() {
        return momentsEnum;
    }

    public void setMomentsEnum(MomentsEnum momentsEnum) {
        this.momentsEnum = momentsEnum;
    }

    public String getMomentsId() {
        return momentsId;
    }

    public void setMomentsId(String momentsId) {
        this.momentsId = momentsId;
    }

    public String getMomentsTitle() {
        return momentsTitle;
    }

    public void setMomentsTitle(String momentsTitle) {
        this.momentsTitle = momentsTitle;
    }

    public String getPublisherUserId() {
        return publisherUserId;
    }

    public void setPublisherUserId(String publisherUserId) {
        this.publisherUserId = publisherUserId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getMomentsImage() {
        return momentsImage;
    }

    public void setMomentsImage(String momentsImage) {
        this.momentsImage = momentsImage;
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
}
