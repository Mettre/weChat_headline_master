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
        this(context,null);
    }

    public MomentsHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
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
}
