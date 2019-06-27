package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.UserBean;
import com.chaychan.news.model.entity.VisitorBean;
import com.chaychan.news.ui.adapter.VisitorAdapter;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.VisitorListPresenter;
import com.chaychan.news.utils.DisplayUtils;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.NetWorkUtils;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.VisitorListListener;
import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.github.nukc.stateview.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import flyn.Eyes;

/**
 * 我的访问记录
 */
public class VisitorListActivity extends BaseActivity<VisitorListPresenter> implements View.OnClickListener, VisitorListListener<VisitorBean>, BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private VisitorAdapter visitorAdapter;
    private List<VisitorBean> visitorBeanList = new ArrayList<>();

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.tip_view)
    TipView mTipView;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Bind(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;

    private int pageSize = 10;

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, VisitorListActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_feedback_list;
    }

    @Override
    public void initView() {

        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setText("我的访客列表");


        mRefreshLayout.setDelegate(this);
        mRvComment.setLayoutManager(new GridLayoutManager(this, 1));
        mRvComment.addItemDecoration(new DisplayUtils.SimpleDividerItemDecoration(1));
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.color_F3F5F4);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字

        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mRvComment);


        visitorAdapter = new VisitorAdapter(this, R.layout.item_visitor, visitorBeanList);
        mRvComment.setAdapter(visitorAdapter);
        visitorAdapter.setOnLoadMoreListener(this, mRvComment);

        visitorAdapter.setEmptyView(R.layout.pager_no_faceback);
    }


    private UserBean getUserBean(VisitorBean visitorBean) {
        UserBean userInfo = new UserBean();
        userInfo.setPublisherHeadAvatar(visitorBean.getHeadAvatar());
        userInfo.setPublisherUserId(visitorBean.getUserId());
        userInfo.setPublisherUserName(visitorBean.getUserName());
        return userInfo;
    }

    @Override
    public void initListener() {
        iv_back.setOnClickListener(this);
        visitorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MomentsActivity.startActivity(visitorBeanList.get(i).getUserId(), (Context) mActivities, getUserBean(visitorBeanList.get(i)));
            }
        });
        visitorAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                new SweetAlertDialog(VisitorListActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("是否删除该条访问记录")
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                mPresenter.deleteVisitor(visitorBeanList.get(i).getVisitorId(), i);

                            }
                        })
                        .show();


                return true;
            }
        });
    }

    @Override
    protected VisitorListPresenter createPresenter() {
        return new VisitorListPresenter(this);
    }


    @Override
    public void initData() {
        mStateView = StateView.inject(mFlContent);
        if (mStateView != null) {
            mStateView.setLoadingResource(R.layout.page_loading);
            mStateView.setRetryResource(R.layout.page_net_error);
        }
        mStateView.showLoading();
        mPresenter.getRefreshVisitorList(page, pageSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


    @Override
    public void onGetRefreshListSuccess(BasePageEntity<VisitorBean> response) {

        if (page == 1) {
            mRefreshLayout.endRefreshing();// 加载完毕后在 UI 线程结束下拉刷新
            visitorAdapter.setEnableLoadMore(response.getPages() > response.getCurrent());

            if (ListUtils.isEmpty(response.getRecords())) {
                //获取不到数据,显示空布局
                mStateView.showEmpty();
                return;
            }
            mStateView.showContent();//显示内容

            visitorBeanList.clear();
            visitorBeanList.addAll(response.getRecords());
            visitorAdapter.notifyDataSetChanged();

        } else {
            visitorAdapter.loadMoreComplete();
            visitorAdapter.setEnableLoadMore(response.getPages() > response.getCurrent());
            if (!ListUtils.isEmpty(response.getRecords())) {
                visitorBeanList.addAll(response.getRecords());
                visitorAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDeleteSuccess(int position) {
        visitorAdapter.remove(position);
    }


    @Override
    public void onError() {
        mTipView.show();//弹出提示

        if (ListUtils.isEmpty(visitorBeanList)) {
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
    public void onDeleteError() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!NetWorkUtils.isNetworkAvailable(this)) {
            //网络不可用弹出提示
            mTipView.show();
            if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
                mRefreshLayout.endRefreshing();
            }
            return;
        }
        page = 1;
        mPresenter.getRefreshVisitorList(page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        mPresenter.getRefreshVisitorList(page, pageSize);
    }
}
