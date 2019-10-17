package com.chaychan.news.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.FindUserBean;
import com.chaychan.news.utils.GlideUtils;

import java.util.List;

public class FindUserAdapter extends BaseQuickAdapter<FindUserBean, BaseViewHolder> {

    private Context mContext;
    private FollowListener followListener;

    public FindUserAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<FindUserBean> data, FollowListener followListener) {
        super(layoutResId, data);
        mContext = context;
        this.followListener = followListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, FindUserBean followBean) {

        GlideUtils.loadRound(mContext, followBean.getHeadAvatar(), helper.getView(R.id.icon_head));
        helper.setText(R.id.userId_text, followBean.getUserId());

        helper.setText(R.id.user_name, followBean.getUserName());
        helper.getView(R.id.tv_button).setEnabled(!followBean.isIsFollow());
        if (followBean.isIsFollow()) {
            helper.setText(R.id.tv_button, "已关注");
            helper.setTextColor(R.id.tv_button, mContext.getResources().getColor(R.color.gray_light));
        } else {
            helper.setText(R.id.tv_button, "添加关注");
            helper.setTextColor(R.id.tv_button, mContext.getResources().getColor(R.color.white));
        }

        helper.getView(R.id.tv_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followBean.isIsFollow()) {
                    followListener.cancelFollow(followBean.getUserId(), helper.getPosition());
                } else {
                    followListener.addFollow(followBean.getUserId(), helper.getPosition());
                }
            }
        });
    }


    public interface FollowListener {

        void addFollow(String followedUser, int position);

        void cancelFollow(String followedUser, int position);
    }
}
