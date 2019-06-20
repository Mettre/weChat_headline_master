package com.chaychan.news.ui.adapter.moments;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.adapter.MomentsAdapter;
import com.chaychan.news.utils.GlideUtils;
import com.socks.library.KLog;

public class PhotoMomentsProvider extends BaseMomentsItemProvider {

    public PhotoMomentsProvider() {
        super();
    }

    @Override
    public int viewType() {
        return MomentsAdapter.CENTER_SINGLE_PIC_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_photo_moments;
    }

    @Override
    protected void setData(BaseViewHolder helper, Moments moments) {
        helper.setText(R.id.moments_word, moments.getMomentsTitle());
        GlideUtils.load(mContext, moments.getMomentsImage(), helper.getView(R.id.img_photo));
    }

}
