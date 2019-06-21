package com.chaychan.news.ui.adapter.moments;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.activity.ImageViewPagerActivity;
import com.chaychan.news.ui.activity.MomentsActivity;
import com.chaychan.news.ui.adapter.MomentsAdapter;
import com.chaychan.news.utils.GlideUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

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
        ((ImageView) helper.getView(R.id.img_photo)).setMaxHeight(250);
        ((ImageView) helper.getView(R.id.img_photo)).setMaxWidth(400);
        ((ImageView) helper.getView(R.id.img_photo)).setAdjustViewBounds(true);
        ArrayList<String> mUrls = new ArrayList<>();
        mUrls.add(moments.getMomentsImage());
        helper.setOnClickListener(R.id.img_photo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewPagerActivity.startAlineActivity(mUrls, 0, mContext, true);
            }
        });
    }

}
