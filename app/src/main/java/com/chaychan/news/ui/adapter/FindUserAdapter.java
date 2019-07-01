package com.chaychan.news.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.FindUserBean;
import com.chaychan.news.utils.GlideUtils;

import java.util.List;

public class FindUserAdapter extends BaseQuickAdapter<FindUserBean, BaseViewHolder> {

    private Context mContext;

    public FindUserAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<FindUserBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FindUserBean followBean) {

        GlideUtils.loadRound(mContext, followBean.getHeadAvatar(), helper.getView(R.id.icon_head));
        helper.setText(R.id.userId_text, followBean.getUserId());

        helper.setText(R.id.user_name, followBean.getUserName());
        helper.getView(R.id.tv_button).setEnabled(followBean.isIsFollow());

    }
}
