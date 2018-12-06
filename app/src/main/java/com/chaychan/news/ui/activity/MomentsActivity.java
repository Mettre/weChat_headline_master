package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.PersonalMomentsRecord;
import com.chaychan.news.ui.adapter.MomentsAdapter;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.PersonalMomentsListPresenter;
import com.chaychan.news.ui.view.MomentsHeaderView;
import com.chaychan.news.utils.DisplayUtils;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.MomentsRecordHelper;
import com.chaychan.news.utils.PersonalMomentsRecordHelper;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.PersonalMomentsListView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.github.nukc.stateview.StateView;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MomentsActivity extends BaseActivity<PersonalMomentsListPresenter> implements PersonalMomentsListView, BaseQuickAdapter.RequestLoadMoreListener {

    public static final String PUBLISHER_USER = "publisherUserId";

    private MomentsAdapter mCommentAdapter;
    protected MomentsHeaderView mHeaderView;

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private List<Moments> momentsList = new ArrayList<>();
    private PersonalMomentsRecord momentsRecord;
    private Gson mGson = new Gson();
    private String publisherUserId;

    private LinearLayoutManager layoutManager;


    public static void startAlineActivity(String publisherUserId, Context mContext) {
        Intent intent = new Intent(mContext, ImageViewPagerActivity.class);
        intent.putExtra(MomentsActivity.PUBLISHER_USER, publisherUserId);
        mContext.startActivity(intent);
    }


    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getMoreMomentsList(publisherUserId, page);
    }

    @Override
    protected PersonalMomentsListPresenter createPresenter() {
        return new PersonalMomentsListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_moments;
    }

    @Override
    public void initView() {
        layoutManager = (LinearLayoutManager) mRvComment.getLayoutManager();
        mRvComment.setLayoutManager(layoutManager);
        mRvComment.addItemDecoration(new DisplayUtils.SpacesItemDecoration());

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
        publisherUserId = intent.getStringExtra(PUBLISHER_USER);
        momentsRecord = PersonalMomentsRecordHelper.getLastNewsRecord(publisherUserId);
        if (momentsRecord == null) {
            //找不到记录，拉取网络数据
            momentsRecord = new PersonalMomentsRecord();
            mPresenter.getRefreshMomentsList(publisherUserId);
            return;
        }

        //找到最后一组记录，转换成新闻集合并展示
        List<Moments> newsList = MomentsRecordHelper.convertToNewsList(momentsRecord.getJson());
        momentsList.addAll(newsList);//添加到集合中
        mCommentAdapter.notifyDataSetChanged();//刷新adapter

        mStateView.showContent();//显示内容
    }

    @Override
    public void initListener() {
        mCommentAdapter = new MomentsAdapter(momentsList);
        mRvComment.setAdapter(mCommentAdapter);

        mHeaderView = new MomentsHeaderView(this);
        mCommentAdapter.addHeaderView(mHeaderView);

        mCommentAdapter.setEnableLoadMore(false);
        mCommentAdapter.setOnLoadMoreListener(this, mRvComment);

        mCommentAdapter.setEmptyView(R.layout.pager_no_comment);
        mCommentAdapter.setHeaderAndEmpty(true);

//        int llInfoBottom = mHeaderView.mLlInfo.getBottom();
//        mRvComment.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                int position = layoutManager.findFirstVisibleItemPosition();
//                View firstVisiableChildView = layoutManager.findViewByPosition(position);
//                int itemHeight = firstVisiableChildView.getHeight();
//                int scrollHeight = (position) * itemHeight - firstVisiableChildView.getTop();
//
//                KLog.i("scrollHeight: " + scrollHeight);
//                KLog.i("llInfoBottom: " + llInfoBottom);
//
//                mLlUser.setVisibility(scrollHeight > llInfoBottom ? View.VISIBLE : View.GONE);//如果滚动超过用户信息一栏，显示标题栏中的用户头像和昵称
//            }
//        });
    }


    @Override
    public void onGetNewsListSuccess(BasePageEntity<Moments> response) {

        mHeaderView.setDetail(response);

        mCommentAdapter.setEnableLoadMore(response.getPages() > response.getCurrent());


        momentsList = response.getRecords();
        if (ListUtils.isEmpty(momentsList)) {
            //获取不到数据,显示空布局
            mStateView.showEmpty();
            return;
        }
        mStateView.showContent();//显示内容

        if (ListUtils.isEmpty(momentsList)) {
            //已经获取不到新闻了，处理出现获取不到新闻的情况
            UIUtils.showToast(UIUtils.getString(R.string.no_news_now));
            return;
        }
        momentsList.addAll(0, momentsList);
        mCommentAdapter.notifyDataSetChanged();
        //保存到数据库
        MomentsRecordHelper.save(publisherUserId, mGson.toJson(momentsList));
    }

    @Override
    public void onError() {

        if (ListUtils.isEmpty(momentsList)) {
            mStateView.showRetry();//显示重试的布局
        }
    }

    @Override
    public void onMoreMomentsSuccess(BasePageEntity<Moments> response) {
        mCommentAdapter.loadMoreComplete();
        if (!ListUtils.isEmpty(response.getRecords())) {
            momentsList.addAll(0, response.getRecords());
            mCommentAdapter.notifyDataSetChanged();
            MomentsRecordHelper.save(publisherUserId, mGson.toJson(response.getRecords()));
        }
    }
}
