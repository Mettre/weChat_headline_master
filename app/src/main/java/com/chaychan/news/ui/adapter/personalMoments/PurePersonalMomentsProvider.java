package com.chaychan.news.ui.adapter.personalMoments;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.adapter.PersonalMomentsAdapter;

import java.util.List;

public class PurePersonalMomentsProvider extends BasePersonalMomentsProvider {

    public PurePersonalMomentsProvider(List<Moments> data) {
        super(data);
    }

    @Override
    public int viewType() {
        return PersonalMomentsAdapter.TEXT_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_personal_pure_moments;
    }

    @Override
    protected void setData(BaseViewHolder helper, Moments moments) {

        helper.setText(R.id.moments_word, moments.getMomentsTitle());
    }
}
