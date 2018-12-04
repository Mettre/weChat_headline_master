package com.chaychan.news.enum_;

import com.chaychan.news.ui.adapter.MomentsAdapter;

public enum MomentsEnum {

    PURE_TEXT(MomentsAdapter.TEXT_NEWS), PHOTO(MomentsAdapter.CENTER_SINGLE_PIC_NEWS);

    public int momentsType;

    MomentsEnum(int momentsType) {
        this.momentsType = momentsType;
    }

    public int getMomentsType(int momentsType) {
        return momentsType;
    }
}
