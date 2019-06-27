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

public class FindUserAdapter extends BaseQuickAdapter<FollowBean, BaseViewHolder> {

    private Context mContext;

    public FindUserAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<FollowBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FollowBean visitorBean) {

        GlideUtils.loadRound(mContext, "http://img4.duitang.com/uploads/item/201407/16/20140716132526_TcyTY.thumb.600_0.jpeg", helper.getView(R.id.icon_head));
        helper.setText(R.id.moments_word, visitorBean.getFollowedUser());

        helper.setText(R.id.user_name, visitorBean.getFollowedUserName())
                .setText(R.id.tv_time, TimeUtils.getShortTime(visitorBean.getUpdateTime()));

    }
}
