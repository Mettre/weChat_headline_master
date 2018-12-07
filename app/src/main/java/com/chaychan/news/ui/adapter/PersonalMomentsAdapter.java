package com.chaychan.news.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.adapter.personalMoments.PhotoPersonalMomentsProvider;
import com.chaychan.news.ui.adapter.personalMoments.PurePersonalMomentsProvider;

import java.util.List;

public class PersonalMomentsAdapter extends MultipleItemRvAdapter<Moments, BaseViewHolder> {

    /**
     * 纯文字布局
     */
    public static final int TEXT_NEWS = 100;
    /**
     * 居中大图布局
     */
    public static final int CENTER_SINGLE_PIC_NEWS = 200;

    private List<Moments> data;

    public PersonalMomentsAdapter(@Nullable List<Moments> data) {
        super(data);
        this.data = data;
        finishInitialize();
    }

    @Override
    protected int getViewType(Moments moments) {
        return moments.getMomentsType().momentsType;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider(new PhotoPersonalMomentsProvider(data));
        mProviderDelegate.registerProvider(new PurePersonalMomentsProvider(data));
    }
}
