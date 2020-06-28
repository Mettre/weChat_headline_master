package com.chaychan.news.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.news.R;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.enum_.MomentsEnum;
import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.MomentsRecord;
import com.chaychan.news.model.entity.UserHeadInfo;
import com.chaychan.news.model.entity.UserInfo;
import com.chaychan.news.ui.activity.MomentsDetailsActivity;
import com.chaychan.news.ui.activity.ReleaseMomentsActivity;
import com.chaychan.news.ui.adapter.MomentsAdapter;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.presenter.MomentsListPresenter;
import com.chaychan.news.ui.view.MomentsHeaderView;
import com.chaychan.news.utils.DisplayUtils;
import com.chaychan.news.utils.GlideUtils;
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

import org.greenrobot.eventbus.Subscribe;

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

    public UserInfo userBean;

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Bind(R.id.iv_detail)
    ImageView iv_detail;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private List<Moments> momentsList = new ArrayList<>();
    private MomentsRecord momentsRecord;
    private Gson mGson = new Gson();
    private String authorities = MyApp.getInstances().getToken();

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
    }


    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        if (event.EventType == StartBrotherEvent.MOMENTSFRAGMENT) {
//            getInformation();
        } else if (event.EventType == StartBrotherEvent.RELEASEMOMENTS) {
            mRefreshLayout.beginRefreshing();
        } else if (event.EventType == StartBrotherEvent.REFRESHTAGE) {
            mRefreshLayout.beginRefreshing();
        } else if (event.EventType == StartBrotherEvent.LOUGINOUT) {
            momentsList.clear();
            mCommentAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 加载个人信息
     */
    private void getInformation() {
        userBean = MyApp.getInstances().getUserInfo();

        UserHeadInfo userHeadInfo = new UserHeadInfo();
        userHeadInfo.setBackGroundWall(userBean.getBackgroundWall());
        userHeadInfo.setUserId(userBean.getUserId());
        userHeadInfo.setHeadAvatar(userBean.getHeadAvatar());
        userHeadInfo.setUserName(userBean.getUserName());
        userHeadInfo.setSignature(userBean.getSignature());
        mHeaderView.setDetail(userHeadInfo);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mCommentAdapter = new MomentsAdapter(momentsList);
        mRvComment.setAdapter(mCommentAdapter);

        mHeaderView = new MomentsHeaderView(mActivity);
//        UserHeadInfo userHeadInfo = new UserHeadInfo();
//        userHeadInfo.setBackGroundWall(userBean.getBackgroundWall());
//        userHeadInfo.setUserId(userBean.getUserId());
//        userHeadInfo.setHeadAvatar(userBean.getHeadAvatar());
//        userHeadInfo.setUserName(userBean.getUserName());
//        userHeadInfo.setSignature(userBean.getSignature());
//        mHeaderView.setDetail(userHeadInfo);
        mCommentAdapter.addHeaderView(mHeaderView);

        mCommentAdapter.setEnableLoadMore(false);
        mCommentAdapter.setOnLoadMoreListener(this, mRvComment);

//        mCommentAdapter.setEmptyView(R.layout.pager_no_comment);
//        mCommentAdapter.setHeaderAndEmpty(true);

        iv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReleaseMomentsActivity.startActivity(mActivity, MomentsEnum.PHOTO);
            }
        });
        iv_detail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ReleaseMomentsActivity.startActivity(mActivity, MomentsEnum.PURE_TEXT);
                return true;
            }
        });
        mCommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MomentsDetailsActivity.startAlineActivity(momentsList.get(i), mActivity);
            }
        });
    }

    @Override
    public void loadData() {
        mStateView.showLoading();

        //查找该频道的最后一组记录
        momentsRecord = MomentsRecordHelper.getLastNewsRecord(authorities);
        if (momentsRecord == null) {
            //找不到记录，拉取网络数据
            momentsRecord = new MomentsRecord();//创建一个没有数据的对象
            mPresenter.getRefreshMomentsList();
            return;
        }

        //找到最后一组记录，转换成新闻集合并展示
        List<Moments> newsList = MomentsRecordHelper.convertToNewsList(momentsRecord.getJson());
        momentsList.addAll(newsList);//添加到集合中
        mCommentAdapter.notifyDataSetChanged();//刷新adapter

        mStateView.showContent();//显示内容
    }

    @Override
    public void onGetNewsListSuccess(BasePageEntity<Moments> response) {
        mRefreshLayout.endRefreshing();// 加载完毕后在 UI 线程结束下拉刷新
        mCommentAdapter.setEnableLoadMore(response.getPages() > response.getCurrent());

        mStateView.showContent();//显示内容

        if (ListUtils.isEmpty(response.getRecords())) {
            //已经获取不到新闻了，处理出现获取不到新闻的情况
//            UIUtils.showToast(UIUtils.getString(R.string.no_news_now));
            return;
        }
        momentsList.clear();
        momentsList.addAll(response.getRecords());
        mCommentAdapter.notifyDataSetChanged();
        //保存到数据库
        MomentsRecordHelper.save(authorities, mGson.toJson(momentsList));
    }


    @Override
    public void onMoreMomentsSuccess(BasePageEntity<Moments> response) {

        mCommentAdapter.loadMoreComplete();
        mCommentAdapter.setEnableLoadMore(response.getPages() > response.getCurrent());
        if (!ListUtils.isEmpty(response.getRecords())) {
            momentsList.addAll(response.getRecords());
            mCommentAdapter.notifyDataSetChanged();
            //保存到数据库
            MomentsRecordHelper.save(authorities, mGson.toJson(response.getRecords()));
        }
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

        if (mRefreshLayout.isLoadingMore()) {
            mRefreshLayout.endLoadingMore();
        }
    }


    @Override
    public void onLoadMoreRequested() {
        // BaseRecyclerViewAdapterHelper的加载更多
        page++;
        mPresenter.getMoreMomentsList(page);
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
        mPresenter.getRefreshMomentsList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(MomentsFragment.this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterEventBus(MomentsFragment.this);
    }
}
