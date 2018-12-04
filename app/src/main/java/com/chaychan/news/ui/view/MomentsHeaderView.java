package com.chaychan.news.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.chaychan.news.R;

import butterknife.ButterKnife;

public class MomentsHeaderView extends FrameLayout {

    private Context mContext;

    public MomentsHeaderView(@NonNull Context context) {
        super(context);
    }

    public MomentsHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MomentsHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void initView() {
        inflate(getContext(), R.layout.header_news_detail, this);
        ButterKnife.bind(this, this);
    }
}
