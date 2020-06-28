package com.chaychan.news.model.entity;

import com.chaychan.news.enum_.MomentsEnum;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Moments extends DataSupport implements Serializable {

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
    private String creationTime;
    private String momentsImage;
    private String date;//日期  2019-12-12
    private UserBean userBean;
    private MomentsEnum momentsType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getMomentsImage() {
        return momentsImage;
    }

    public void setMomentsImage(String momentsImage) {
        this.momentsImage = momentsImage;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public MomentsEnum getMomentsType() {
        return momentsType;
    }

    public void setMomentsType(MomentsEnum momentsType) {
        this.momentsType = momentsType;
    }
}
