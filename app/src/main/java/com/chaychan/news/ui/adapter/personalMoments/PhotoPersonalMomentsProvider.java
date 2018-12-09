package com.chaychan.news.ui.adapter.personalMoments;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.activity.ImageViewPagerActivity;
import com.chaychan.news.ui.activity.MomentsDetailsActivity;
import com.chaychan.news.ui.adapter.PersonalMomentsAdapter;
import com.chaychan.news.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class PhotoPersonalMomentsProvider extends BasePersonalMomentsProvider {

    public PhotoPersonalMomentsProvider(List<Moments> data) {
        super(data);

    }

    @Override
    public int viewType() {
        return PersonalMomentsAdapter.CENTER_SINGLE_PIC_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_personal_photo_moments;
    }

    @Override
    protected void setData(BaseViewHolder helper, Moments moments) {

        helper.setText(R.id.moments_word, moments.getMomentsTitle());
        GlideUtils.load(mContext, moments.getMomentsImage(), helper.getView(R.id.img_photo));
        helper.setOnClickListener(R.id.theme_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<String> mUrls = new ArrayList<String>();
//                mUrls.add(moments.getMomentsImage());
//                ImageViewPagerActivity.startAlineActivity(mUrls, 0, mContext);

                MomentsDetailsActivity.startAlineActivity(moments.getMomentsId(), mContext);
            }
        });
    }
}
