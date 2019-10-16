package com.chaychan.news.model.entity;

import com.chaychan.news.enum_.MomentsEnum;

public class ReleaseMoments {

    private String momentsTitle;

    private String momentsImage;

    private MomentsEnum momentsType;

    public String getMomentsTitle() {
        return momentsTitle;
    }

    public void setMomentsTitle(String momentsTitle) {
        this.momentsTitle = momentsTitle;
    }

    public String getMomentsImage() {
        return momentsImage;
    }

    public void setMomentsImage(String momentsImage) {
        this.momentsImage = momentsImage;
    }

    public MomentsEnum getMomentsType() {
        return momentsType;
    }

    public void setMomentsType(MomentsEnum momentsType) {
        this.momentsType = momentsType;
    }
}
