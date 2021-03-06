package com.chaychan.news.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.FeedbackBean;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.TimeUtils;

import java.util.List;

public class FeedbackAdapter extends BaseQuickAdapter<FeedbackBean, BaseViewHolder> {

    private Context mContext;

    public FeedbackAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<FeedbackBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedbackBean feedbackBean) {

        GlideUtils.load(mContext, feedbackBean.getHeadAvatar(), helper.getView(R.id.icon_head));
        helper.setText(R.id.moments_word, feedbackBean.getContent());

        helper.setText(R.id.user_name, feedbackBean.getUserName())
                .setText(R.id.tv_time, TimeUtils.getShortTime(feedbackBean.getCreationTime()));

    }

}
