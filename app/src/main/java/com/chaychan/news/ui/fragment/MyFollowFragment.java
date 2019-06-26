package com.chaychan.news.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.FollowBean;
import com.chaychan.news.ui.adapter.FollowAdapter;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.presenter.FollowPresenter;
import com.chaychan.news.utils.DisplayUtils;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.NetWorkUtils;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.FollowListener;
import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.github.nukc.stateview.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 我的关注
 */
public class MyFollowFragment extends BaseFragment<FollowPresenter> implements FollowListener<FollowBean>, BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private FollowAdapter followAdapter;
    private List<FollowBean> followBeanList = new ArrayList<>();

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.tip_view)
    TipView mTipView;

    @Bind(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    private int pageSize = 10;

    public static MyFollowFragment newInstance() {
        MyFollowFragment fragment = new MyFollowFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_follow;
    }

    @Override
    public void initData() {

        mRefreshLayout.setDelegate(this);
        mRvComment.setLayoutManager(new GridLayoutManager(mActivity, 1));
        mRvComment.addItemDecoration(new DisplayUtils.SimpleDividerItemDecoration(1));
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


        followAdapter = new FollowAdapter(mActivity, R.layout.item_visitor, followBeanList);
        mRvComment.setAdapter(followAdapter);
        followAdapter.setOnLoadMoreListener(this, mRvComment);

        followAdapter.setEmptyView(R.layout.pager_no_faceback);
    }

    @Override
    protected FollowPresenter createPresenter() {
        return new FollowPresenter(this);
    }

    @Override
    protected void loadData() {
        mStateView = StateView.inject(mFlContent);
        SoftUtils.setupUI(mActivity, groupView);
        if (mStateView != null) {
            mStateView.setLoadingResource(R.layout.page_loading);
            mStateView.setRetryResource(R.layout.page_net_error);
        }
        mStateView.showLoading();
        mPresenter.getRefreshFollowList(page, pageSize);
    }


    @Override
    public void onGetRefreshListSuccess(BasePageEntity<FollowBean> response) {
        if (page == 1) {
            mRefreshLayout.endRefreshing();// 加载完毕后在 UI 线程结束下拉刷新
            followAdapter.setEnableLoadMore(response.getPages() > response.getCurrent());

            if (ListUtils.isEmpty(response.getRecords())) {
                //获取不到数据,显示空布局
                mStateView.showEmpty();
                return;
            }
            mStateView.showContent();//显示内容

            followBeanList.clear();
            followBeanList.addAll(response.getRecords());
            followAdapter.notifyDataSetChanged();

        } else {
            followAdapter.loadMoreComplete();
            followAdapter.setEnableLoadMore(response.getPages() > response.getCurrent());
            if (!ListUtils.isEmpty(response.getRecords())) {
                followBeanList.addAll(response.getRecords());
                followAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onError() {
        mTipView.show();//弹出提示

        if (ListUtils.isEmpty(followBeanList)) {
            //如果一开始进入没有数据
            mStateView.showRetry();//显示重试的布局
        }
        //收起刷新
        if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
            mRefreshLayout.endRefreshing();
        }
        if (mRefreshLayout.isLoadingMore()) {
            mRefreshLayout.endLoadingMore();
        }
    }


    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getRefreshFollowList(page, pageSize);
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
        page = 1;
        mPresenter.getRefreshFollowList(page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
