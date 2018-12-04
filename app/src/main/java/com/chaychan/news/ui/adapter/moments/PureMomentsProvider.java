package com.chaychan.news.ui.adapter.moments;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.adapter.MomentsAdapter;

public class PureMomentsProvider extends BaseMomentsItemProvider {

    public PureMomentsProvider() {
        super();
    }

    @Override
    public int viewType() {
        return MomentsAdapter.TEXT_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_pure_text_moments;
    }

    @Override
    protected void setData(BaseViewHolder helper, Moments moments) {

        helper.setText(R.id.moments_word, moments.getMomentsTitle());
    }

}
