package com.chaychan.news.ui.adapter.moments;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.adapter.MomentsAdapter;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.TimeUtils;
import com.socks.library.KLog;

public abstract class BaseMomentsItemProvider extends BaseItemProvider<Moments, BaseViewHolder> {

    public BaseMomentsItemProvider() {
    }

    @Override
    public void convert(BaseViewHolder helper, Moments moments, int i) {
        KLog.e("----------Mettre----------------        4       "+ MomentsAdapter.CENTER_SINGLE_PIC_NEWS);
        GlideUtils.load(mContext, moments.getUserBean().getPublisherHeadAvatar(), helper.getView(R.id.icon_head));
        helper.setText(R.id.user_name, moments.getUserBean().getPublisherUserName())
                .setText(R.id.tv_time, TimeUtils.getShortTime(moments.getCreationTime()));

        setData(helper, moments);
    }

    protected abstract void setData(BaseViewHolder helper, Moments moments);
}
