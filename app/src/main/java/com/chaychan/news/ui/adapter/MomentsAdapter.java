package com.chaychan.news.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.adapter.moments.PhotoMomentsProvider;
import com.chaychan.news.ui.adapter.moments.PureMomentsProvider;
import com.socks.library.KLog;

import java.util.List;

/**
 * 朋友圈
 */
public class MomentsAdapter extends MultipleItemRvAdapter<Moments, BaseViewHolder> {

    /**
     * 纯文字布局
     */
    public static final int TEXT_NEWS = 100;
    /**
     * 居中大图布局
     */
    public static final int CENTER_SINGLE_PIC_NEWS = 200;

    public MomentsAdapter(@Nullable List<Moments> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(Moments moments) {
        KLog.e(moments.getMomentsType().name());
        return moments.getMomentsType().momentsType;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider(new PhotoMomentsProvider());
        mProviderDelegate.registerProvider(new PureMomentsProvider());
    }


}
