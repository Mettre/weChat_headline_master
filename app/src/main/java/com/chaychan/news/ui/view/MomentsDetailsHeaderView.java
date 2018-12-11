package com.chaychan.news.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.UserBean;
import com.chaychan.news.ui.activity.ImageViewPagerActivity;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.TimeUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MomentsDetailsHeaderView extends FrameLayout {

    private Context mContext;

    @Bind(R.id.icon_head)
    ImageView iconHeader;

    @Bind(R.id.user_name)
    TextView userName;

    @Bind(R.id.moments_word)
    TextView momentsWord;

    @Bind(R.id.tv_time)
    TextView tvTime;

    public MomentsDetailsHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public MomentsDetailsHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MomentsDetailsHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.item_pure_text_moments, this);
        ButterKnife.bind(this, this);
    }

    public void setDetail(Moments moments) {
        userName.setText(moments.getUserBean().getPublisherUserName());
        momentsWord.setText(moments.getMomentsTitle());
        tvTime.setText(TimeUtils.getShortTime(moments.getCreationTime()));
        GlideUtils.load(mContext, moments.getUserBean().getPublisherHeadAvatar(), iconHeader);
        iconHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mUrls = new ArrayList<String>();
                mUrls.add(moments.getUserBean().getPublisherHeadAvatar());
                ImageViewPagerActivity.startAlineActivity(mUrls, 0, mContext, true);
            }
        });
    }
}
