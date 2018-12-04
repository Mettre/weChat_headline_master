package com.chaychan.news.ui.adapter.moments;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.TimeUtils;

public abstract class BaseMomentsItemProvider extends BaseItemProvider<Moments, BaseViewHolder> {

    private String mChannelCode;

    public BaseMomentsItemProvider() {
    }

    @Override
    public void convert(BaseViewHolder helper, Moments moments, int i) {

        GlideUtils.load(mContext, moments.getPublisherHeadAvatar(), helper.getView(R.id.icon_head));
        helper.setText(R.id.user_name, moments.getPublisherUserName())
                .setText(R.id.tv_time, TimeUtils.getShortTime(moments.getCreationTime().getTime()));

        setData(helper, moments);
    }

    protected abstract void setData(BaseViewHolder helper, Moments moments);
}
