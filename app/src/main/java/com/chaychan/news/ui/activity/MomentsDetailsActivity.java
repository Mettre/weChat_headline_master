package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chaychan.news.R;
import com.chaychan.news.model.entity.BaseEntity;
import com.chaychan.news.model.entity.MomentsDetailsEntity;
import com.chaychan.news.model.entity.Reply;
import com.chaychan.news.ui.adapter.MomentsDetailsAdapter;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.MomentsDetailsPresenter;
import com.chaychan.news.ui.view.MomentsDetailsHeaderView;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.MomentsDetailsEntityHelper;
import com.chaychan.news.view.MomentsDetailsListener;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.github.nukc.stateview.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import flyn.Eyes;

public class MomentsDetailsActivity extends BaseActivity<MomentsDetailsPresenter> implements MomentsDetailsListener<MomentsDetailsEntity> {

    private String momentsId;
    public static final String MOMENTS_ID = "momentsId";

    private MomentsDetailsAdapter mCommentAdapter;
    protected MomentsDetailsHeaderView mHeaderView;

    private List<Reply> momentsList = new ArrayList<>();
    private MomentsDetailsEntity momentsRecord;

    @Bind(R.id.iv_back)
    ImageView mIvBack;

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private LinearLayoutManager layoutManager;


    public static void startAlineActivity(String momentsId, Context mContext) {
        Intent intent = new Intent(mContext, MomentsDetailsActivity.class);
        intent.putExtra(MomentsDetailsActivity.MOMENTS_ID, momentsId);
        mContext.startActivity(intent);
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_details_moments;
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, R.color.color_222222);
        layoutManager = (LinearLayoutManager) mRvComment.getLayoutManager();
        mRvComment.setLayoutManager(layoutManager);
    }

    @Override
    public void initData() {
        mStateView = StateView.inject(mFlContent);
        if (mStateView != null) {
            mStateView.setLoadingResource(R.layout.page_loading);
            mStateView.setRetryResource(R.layout.page_net_error);
        }

        mStateView.showLoading();
        Intent intent = getIntent();
        momentsId = intent.getStringExtra(MOMENTS_ID);
        momentsRecord = MomentsDetailsEntityHelper.getLastNewsRecord(momentsId);
        if (momentsRecord == null) {
            //找不到记录，拉取网络数据
            momentsRecord = new MomentsDetailsEntity();
            mPresenter.getRefreshMomentsList(momentsId);
            return;
        }

        List<Reply> newsList = momentsRecord.getReply();
        momentsList.addAll(newsList);//添加到集合中
        mHeaderView.setDetail(momentsRecord.getMoments());
        mCommentAdapter.notifyDataSetChanged();//刷新adapter

        mStateView.showContent();//显示内容
    }


    @Override
    public void initListener() {
        mCommentAdapter = new MomentsDetailsAdapter(this, R.layout.item_moment_reply, momentsList);

        mRvComment.setAdapter(mCommentAdapter);

        mHeaderView = new MomentsDetailsHeaderView(this);
        mCommentAdapter.addHeaderView(mHeaderView);

        mCommentAdapter.setEnableLoadMore(false);

        mCommentAdapter.setEmptyView(R.layout.pager_no_comment,mRvComment);
        mCommentAdapter.setHeaderAndEmpty(true);
    }


    @Override
    protected MomentsDetailsPresenter createPresenter() {
        return new MomentsDetailsPresenter(this);
    }

    @Override
    public void onGetListSuccess(BaseEntity<MomentsDetailsEntity> response) {

        mStateView.showContent();//显示内容
        momentsList.clear();
        momentsList.addAll(response.getData().getReply());
        mCommentAdapter.notifyDataSetChanged();
        //保存到数据库
        MomentsDetailsEntityHelper.save(momentsId, response.getData().getMoments(), response.getData().getReply());
    }

    @Override
    public void onError() {
        if (ListUtils.isEmpty(momentsList)) {
            mStateView.showRetry();//显示重试的布局
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
