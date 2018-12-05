package com.chaychan.news.ui.adapter.moments;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.adapter.MomentsAdapter;
import com.socks.library.KLog;

public class PhotoMomentsProvider extends BaseMomentsItemProvider {

    public PhotoMomentsProvider() {
        super();
    }

    @Override
    public int viewType() {
        KLog.e("----------Mettre----------------        2       "+MomentsAdapter.CENTER_SINGLE_PIC_NEWS);
        return MomentsAdapter.CENTER_SINGLE_PIC_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_photo_moments;
    }

    @Override
    protected void setData(BaseViewHolder helper, Moments moments) {
        KLog.e("----------Mettre----------------        33       "+MomentsAdapter.CENTER_SINGLE_PIC_NEWS);
        helper.setText(R.id.moments_word, moments.getMomentsTitle());
    }

}
