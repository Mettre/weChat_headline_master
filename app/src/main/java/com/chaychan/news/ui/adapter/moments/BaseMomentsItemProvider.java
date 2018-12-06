package com.chaychan.news.ui.adapter.moments;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.activity.MomentsActivity;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.TimeUtils;

public abstract class BaseMomentsItemProvider extends BaseItemProvider<Moments, BaseViewHolder> {

    public BaseMomentsItemProvider() {
    }

    @Override
    public void convert(BaseViewHolder helper, Moments moments, int i) {
        GlideUtils.load(mContext, moments.getUserBean().getPublisherHeadAvatar(), helper.getView(R.id.icon_head));
        helper.setText(R.id.user_name, moments.getUserBean().getPublisherUserName())
                .setText(R.id.tv_time, TimeUtils.getShortTime(moments.getCreationTime()));


        helper.setOnClickListener(R.id.icon_head, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentsActivity.startAlineActivity(moments.getUserBean().getPublisherUserId(), mContext);
            }
        });

        setData(helper, moments);
    }

    protected abstract void setData(BaseViewHolder helper, Moments moments);
}
