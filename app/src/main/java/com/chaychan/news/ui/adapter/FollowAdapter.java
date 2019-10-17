package com.chaychan.news.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.FollowBean;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.TimeUtils;

import java.util.List;

public class FollowAdapter extends BaseQuickAdapter<FollowBean, BaseViewHolder> {
    private Context mContext;

    public FollowAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<FollowBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FollowBean followBean) {

        GlideUtils.loadRound(mContext, followBean.getHeadAvatar(), helper.getView(R.id.icon_head));
        helper.setVisible(R.id.follow_each, followBean.isEachOther());
        helper.setText(R.id.user_name, followBean.getFollowedUserName())
                .setText(R.id.tv_time, TimeUtils.getShortTime(followBean.getUpdateTime()));

    }
}
