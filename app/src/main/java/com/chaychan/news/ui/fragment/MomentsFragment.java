package com.chaychan.news.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.MomentsRecord;
import com.chaychan.news.ui.adapter.MomentsAdapter;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.presenter.MomentsListPresenter;
import com.chaychan.news.ui.view.MomentsHeaderView;
import com.chaychan.news.utils.DisplayUtils;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.NetWorkUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.utils.MomentsRecordHelper;
import com.chaychan.news.view.lMomentsListView;
import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author ChayChan
 * @description: 微头条fragment
 * @date 2017/6/12  21:47
 */

public class MomentsFragment extends BaseFragment<MomentsListPresenter> implements lMomentsListView, BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.RequestLoadMoreListener {

    private MomentsAdapter mCommentAdapter;
    protected MomentsHeaderView mHeaderView;

    @Bind(R.id.tip_view)
    TipView mTipView;

    @Bind(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;


    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private List<Moments> momentsList = new ArrayList<>();
    private MomentsRecord momentsRecord;
    private Gson mGson = new Gson();
    private String publisherUserId;

    @Override
    protected MomentsListPresenter createPresenter() {
        return new MomentsListPresenter(this);
    }

    @Override
    public View getStateViewRoot() {
        return mFlContent;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_micro;
    }

    @Override
    public void initView(View rootView) {
        mRefreshLayout.setDelegate(this);
        mRvComment.setLayoutManager(new GridLayoutManager(mActivity, 1));
        mRvComment.addItemDecoration(new DisplayUtils.SpacesItemDecoration());
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mActivity, false);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.color_F3F5F4);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字


        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mRvComment);
    }

    @Override
    public void initData() {
//        publisherUserId = getArguments().getString(Constant.PUBLISHER_USER_ID);
        publisherUserId = "2018111514554801539";
    }

    @Override
    public void initListener() {
        mCommentAdapter = new MomentsAdapter(momentsList);
        mRvComment.setAdapter(mCommentAdapter);

        mHeaderView = new MomentsHeaderView(mActivity);
        mCommentAdapter.addHeaderView(mHeaderView);

        mCommentAdapter.setEnableLoadMore(true);
        mCommentAdapter.setOnLoadMoreListener(this, mRvComment);

        mCommentAdapter.setEmptyView(R.layout.pager_no_comment);
        mCommentAdapter.setHeaderAndEmpty(true);
    }

    @Override
    public void loadData() {
        mStateView.showLoading();

        //查找该频道的最后一组记录
        momentsRecord = MomentsRecordHelper.getLastNewsRecord(publisherUserId);
        if (momentsRecord == null) {
            //找不到记录，拉取网络数据
            momentsRecord = new MomentsRecord();//创建一个没有数据的对象
            mPresenter.getMomentsList(publisherUserId);
            return;
        }

        //找到最后一组记录，转换成新闻集合并展示
        List<Moments> newsList = MomentsRecordHelper.convertToNewsList(momentsRecord.getJson());
        momentsList.addAll(newsList);//添加到集合中
        mCommentAdapter.notifyDataSetChanged();//刷新adapter

        mStateView.showContent();//显示内容
    }

    @Override
    public void onGetNewsListSuccess(List<Moments> newList, String tipInfo) {
        mRefreshLayout.endRefreshing();// 加载完毕后在 UI 线程结束下拉刷新

        KLog.e("----------Request Start-----55-----------" + newList.size());
        //如果是第一次获取数据
        if (ListUtils.isEmpty(momentsList)) {
            if (ListUtils.isEmpty(newList)) {
                //获取不到数据,显示空布局
                mStateView.showEmpty();
                return;
            }
            mStateView.showContent();//显示内容
        }

        if (ListUtils.isEmpty(newList)) {
            //已经获取不到新闻了，处理出现获取不到新闻的情况
            UIUtils.showToast(UIUtils.getString(R.string.no_news_now));
            return;
        }
        momentsList.addAll(0, newList);
        mCommentAdapter.notifyDataSetChanged();

        mTipView.show(tipInfo);
        KLog.e("----------Request Start-----66-----------" + newList.size());
        //保存到数据库
        MomentsRecordHelper.save(publisherUserId, mGson.toJson(newList));
    }

    @Override
    public void onError() {
        mTipView.show();//弹出提示

        if (ListUtils.isEmpty(momentsList)) {
            //如果一开始进入没有数据
            mStateView.showRetry();//显示重试的布局
        }

        //收起刷新
        if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
            mRefreshLayout.endRefreshing();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        // BaseRecyclerViewAdapterHelper的加载更多
        if (momentsRecord.getPage() == 0 || momentsRecord.getPage() == 1) {
            //如果记录的页数为0(即是创建的空记录)，或者页数为1(即已经是第一条记录了)
            //mRefreshLayout.endLoadingMore();//结束加载更多
            mCommentAdapter.loadMoreEnd();
            return;
        }

        MomentsRecord preNewsRecord = MomentsRecordHelper.getPreNewsRecord(publisherUserId, momentsRecord.getPage());
        if (preNewsRecord == null) {
            // mRefreshLayout.endLoadingMore();//结束加载更多
            mCommentAdapter.loadMoreEnd();
            return;
        }

        momentsRecord = preNewsRecord;

        long startTime = System.currentTimeMillis();

        List<Moments> momentsList = MomentsRecordHelper.convertToNewsList(momentsRecord.getJson());
        KLog.e(momentsList);

        long endTime = System.currentTimeMillis();

        //由于是读取数据库，如果耗时不足1秒，则1秒后才收起加载更多
        if (endTime - startTime <= 1000) {
            UIUtils.postTaskDelay(new Runnable() {
                @Override
                public void run() {
                    mCommentAdapter.loadMoreComplete();
                    momentsList.addAll(momentsList);//添加到集合下面
                    mCommentAdapter.notifyDataSetChanged();//刷新adapter
                }
            }, (int) (1000 - (endTime - startTime)));
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!NetWorkUtils.isNetworkAvailable(mActivity)) {
            //网络不可用弹出提示
            mTipView.show();
            if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
                mRefreshLayout.endRefreshing();
            }
            return;
        }
        mPresenter.getMomentsList(publisherUserId);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
