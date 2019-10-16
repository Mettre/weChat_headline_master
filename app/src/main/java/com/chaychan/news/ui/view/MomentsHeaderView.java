package com.chaychan.news.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.NewsDetail;
import com.chaychan.news.model.entity.UserHeadInfo;
import com.chaychan.news.ui.activity.ImageViewPagerActivity;
import com.chaychan.news.utils.GlideUtils;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MomentsHeaderView extends FrameLayout {

    private Context mContext;

    @Bind(R.id.icon_head)
    ImageView iconHeader;

    @Bind(R.id.user_name)
    TextView userName;

    @Bind(R.id.signature_text)
    TextView signatureText;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    public MomentsHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public MomentsHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MomentsHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.header_moments, this);
        ButterKnife.bind(this, this);

    }

    public void setDetail(UserHeadInfo userHeadInfo) {
        KLog.a(new Gson().toJson(userHeadInfo));
        userName.setText(userHeadInfo.getUserName());
        if (TextUtils.isEmpty(userHeadInfo.getSignature())) {
            signatureText.setVisibility(GONE);
        } else {
            signatureText.setVisibility(VISIBLE);
            signatureText.setText(userHeadInfo.getSignature());
        }
        GlideUtils.load(mContext, userHeadInfo.getHeadAvatar(), iconHeader);
        GlideUtils.load(mContext, userHeadInfo.getBackGroundWall(), iv_back);
        iconHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mUrls = new ArrayList<String>();
                mUrls.add(userHeadInfo.getHeadAvatar());
                ImageViewPagerActivity.startAlineActivity(mUrls, 0, mContext, true);
            }
        });


    }
}
