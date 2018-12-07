package com.chaychan.news.ui.adapter.personalMoments;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.utils.TimeUtils;

import java.util.List;

public abstract class BasePersonalMomentsProvider extends BaseItemProvider<Moments, BaseViewHolder> {

    private List<Moments> data;

    public BasePersonalMomentsProvider(List<Moments> data) {
        this.data = data;
    }

    @Override
    public void convert(BaseViewHolder helper, Moments moments, int i) {

        if (helper.getAdapterPosition() == 0) {
            helper.setVisible(R.id.first_item_blank, true);
            helper.setVisible(R.id.first_item_title, true);
        } else {
            if (moments.getDate() != data.get(helper.getPosition() - 1).getDate()) {
                helper.setVisible(R.id.first_item_blank, true);
                helper.setVisible(R.id.first_item_title, true);
            } else {
                helper.setVisible(R.id.first_item_blank, false);
                helper.setVisible(R.id.first_item_title, false);
            }
        }
        helper.setText(R.id.tv_time,TimeUtils.getShortTime(moments.getCreationTime()));
        setData(helper, moments);
    }

    protected abstract void setData(BaseViewHolder helper, Moments moments);
}
