package com.chaychan.news.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.DataBean;
import com.chaychan.news.model.entity.Friends;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.TimeUtils;

import java.util.List;

public class FriendAdapter extends BaseQuickAdapter<DataBean, BaseViewHolder> {

    private Context mContext;

    public FriendAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<DataBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataBean friends) {
        GlideUtils.loadRound(mContext, friends.getHeadAvatar(), helper.getView(R.id.icon_head));
        helper.setText(R.id.tv_name, friends.getUserName());

    }
}
