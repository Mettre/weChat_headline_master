package com.chaychan.news.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

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
        GlideUtils.load(mContext, reply.getHeadAvatar(), helper.getView(R.id.icon_head));
        if (!TextUtils.isEmpty(reply.getReplyParentId())) {
            String content = "回复" + reply.getReplyParentUserName() + "：" + reply.getReplyContent();
            SpannableString spannableString = new SpannableString(content);
            if (!TextUtils.isEmpty(reply.getReplyParentUserName()) && reply.getReplyParentUserName().length() > 0) {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#406599")), 2, 2 + reply.getReplyParentUserName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            helper.setText(R.id.moments_word, spannableString);
        } else {
            helper.setText(R.id.moments_word, reply.getReplyContent());
        }
        if (helper.getPosition() == 1) {
            helper.setVisible(R.id.first_view, true);
        } else {
            helper.setVisible(R.id.first_view, false);
        }

        helper.setText(R.id.user_name, reply.getUserName())
                .setText(R.id.tv_time, TimeUtils.getShortTime(reply.getCreationTime()));
    }
}
