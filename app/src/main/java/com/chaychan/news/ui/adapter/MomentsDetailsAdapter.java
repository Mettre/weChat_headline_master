package com.chaychan.news.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Reply;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.TimeUtils;

import java.util.List;

public class MomentsDetailsAdapter extends BaseQuickAdapter<Reply, BaseViewHolder> {

    private Context mContext;

    public MomentsDetailsAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<Reply> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Reply reply) {
        GlideUtils.loadRound(mContext,reply.getHeadAvatar(), helper.getView(R.id.iv_avatar));
        helper.setText(R.id.user_name, reply.getUserName())
                .setText(R.id.moments_word, reply.getReplyContent())
                .setText(R.id.tv_time, TimeUtils.getShortTime(reply.getCreationTime()));
    }
}
