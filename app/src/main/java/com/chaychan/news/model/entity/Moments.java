package com.chaychan.news.model.entity;

import com.chaychan.news.enum_.MomentsEnum;

import java.util.Date;

public class Moments {

    /**
     * momentsId : 2018112312430700179
     * momentsTitle : 好想哭啊，我为什么叫阿凡达
     * creationTime : 1542948187000
     * momentsImage : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543548249&di=74b243a26a5d8aac750843dfd39e8cf3&imgtype=jpg&er=1&src=http%3A%2F%2Fs14.sinaimg.cn%2Fmw690%2F002ZriB0zy72JzMLZz7dd%26amp%3B690
     * userBean : {"publisherUserName":"阿凡达","publisherHeadAvatar":"string","publisherUserId":"2018112209485799882"}
     * momentsType : PHOTO
     */

    private String momentsId;
    private String momentsTitle;
    private long creationTime;
    private String momentsImage;
    private Date date;//日期  2019-12-12
    private UserBeanBean userBean;
    private MomentsEnum momentsType;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getMomentsImage() {
        return momentsImage;
    }

    public void setMomentsImage(String momentsImage) {
        this.momentsImage = momentsImage;
    }

    public UserBeanBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBeanBean userBean) {
        this.userBean = userBean;
    }

    public MomentsEnum getMomentsType() {
        return momentsType;
    }

    public void setMomentsType(MomentsEnum momentsType) {
        this.momentsType = momentsType;
    }

    public static class UserBeanBean {
        /**
         * publisherUserName : 阿凡达
         * publisherHeadAvatar : string
         * publisherUserId : 2018112209485799882
         */

        private String publisherUserName;
        private String publisherHeadAvatar;
        private String publisherUserId;

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
}
