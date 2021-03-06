package com.chaychan.news.ui.fragment;

import android.app.Application;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.news.R;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.entity.DataBean;
import com.chaychan.news.model.entity.Friends;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.activity.LoginActivity;
import com.chaychan.news.ui.activity.MainActivity;
import com.chaychan.news.ui.activity.MomentsActivity;
import com.chaychan.news.ui.activity.RecommendedUsersActivity;
import com.chaychan.news.ui.adapter.FriendAdapter;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.presenter.FriendsPresenter;
import com.chaychan.news.utils.DisplayUtils;
import com.chaychan.news.utils.FriendsRecordHelper;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.NetWorkUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.IFriendsListener;
import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import flyn.Eyes;

/**
 * 我的好友
 */
public class FriendFragment extends BaseFragment<FriendsPresenter> implements IFriendsListener<Friends>, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private FriendAdapter friendAdapter;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.tip_view)
    TipView mTipView;

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    @Bind(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;

    @Bind(R.id.recommend_btn)
    TextView recommend_btn;

    private List<DataBean> momentsList = new ArrayList<>();
    private String authorities = MyApp.getInstances().getToken();

    @Override
    protected FriendsPresenter createPresenter() {
        return new FriendsPresenter(this);
    }

    @Override
    public View getStateViewRoot() {
        return mFlContent;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_friends;
    }

    @Override
    public void initView(View rootView) {
        iv_back.setVisibility(View.GONE);
        mTvAuthor.setText("好友列表");
        Eyes.setStatusBarColor(mActivity, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        mRvComment.setLayoutManager(new GridLayoutManager(mActivity, 1));

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
        KLog.i("EventBus接受");
        if (event.EventType == StartBrotherEvent.REFRESHTAGE) {
            mRefreshLayout.beginRefreshing();
        } else if (event.EventType == StartBrotherEvent.LOUGINOUT) {
            momentsList.clear();
            friendAdapter.notifyDataSetChanged();
        } else if (event.EventType == StartBrotherEvent.RECOMMENDEDUSER) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        RecommendedUsersActivity.startActivity(mActivity);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        recommend_btn.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        mStateView.showLoading();
        momentsList = FriendsRecordHelper.selectFriendsRecords(authorities);

        friendAdapter = new FriendAdapter(mActivity, R.layout.item_friends, momentsList);
        mRvComment.setAdapter(friendAdapter);
        friendAdapter.setEnableLoadMore(false);

        friendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MomentsActivity.startActivity(momentsList.get(i).getUserId(), mActivity, null);
            }
        });


        if (ListUtils.isEmpty(momentsList)) {
            //找不到记录，拉取网络数据
            mPresenter.getFriendsList();
            return;
        }

        mStateView.showContent();//显示内容
        mPresenter.getFriendsList();
    }


    @Override
    public void onGetFriendsSuccess(Friends response) {
        mRefreshLayout.endRefreshing();// 加载完毕后在 UI 线程结束下拉刷新
        if (ListUtils.isEmpty(response.getData())) {
            //获取不到数据,显示空布局
            mStateView.showEmpty();
            return;
        }
        mStateView.showContent();//显示内容
        momentsList.clear();
        momentsList.addAll(response.getData());
        friendAdapter.notifyDataSetChanged();
        //保存到数据库
        FriendsRecordHelper.save(momentsList);
    }

    @Override
    public void onError() {
        mTipView.show();//弹出提示

        if (ListUtils.isEmpty(momentsList)) {
            //如果一开始进入没有数据
            mStateView.showRetry();//显示重试的布局
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(FriendFragment.this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterEventBus(FriendFragment.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recommend_btn:
                if (TextUtils.isEmpty(MyApp.getInstances().getToken())) {
                    LoginActivity.startLoginActivity(mActivity, true);
                } else {
                    RecommendedUsersActivity.startActivity(mActivity);
                }
                break;
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
        mPresenter.getFriendsList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
