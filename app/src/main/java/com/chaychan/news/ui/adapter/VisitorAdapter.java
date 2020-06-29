package com.chaychan.news.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.VisitorBean;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.TimeUtils;

import java.util.List;

public class VisitorAdapter extends BaseQuickAdapter<VisitorBean, BaseViewHolder> {

    private Context mContext;

    public VisitorAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<VisitorBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VisitorBean visitorBean) {

        GlideUtils.loadRound(mContext, visitorBean.getHeadAvatar(), helper.getView(R.id.icon_head));
        helper.setText(R.id.moments_word, "访问了你的空间");

        helper.setText(R.id.user_name, visitorBean.getUserName())
                .setText(R.id.tv_time, visitorBean.getCreationTime());

    }
}
