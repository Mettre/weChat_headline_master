package com.chaychan.news.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.Friends;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.adapter.FriendAdapter;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.presenter.FriendsPresenter;
import com.chaychan.news.utils.FriendsRecordHelper;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.IFriendsListener;
import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import flyn.Eyes;

/**
 * 我的好友
 */
public class FriendFragment extends BaseFragment<FriendsPresenter> implements IFriendsListener<ResultList> {

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

    private List<Friends> momentsList = new ArrayList<>();
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
    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
//        friendAdapter = new FriendAdapter(mActivity, R.layout.item_friends, momentsList);
//        mRvComment.setAdapter(friendAdapter);
//        friendAdapter.setEnableLoadMore(false);
//
//        friendAdapter.setEmptyView(R.layout.pager_no_comment, mRvComment);
//        friendAdapter.setHeaderAndEmpty(true);
    }

    @Override
    public void loadData() {
        mStateView.showLoading();
        momentsList = FriendsRecordHelper.selectFriendsRecords(authorities);

        friendAdapter = new FriendAdapter(mActivity, R.layout.item_friends, momentsList);
        mRvComment.setAdapter(friendAdapter);
        friendAdapter.setEnableLoadMore(false);

        if (ListUtils.isEmpty(momentsList)) {
            //找不到记录，拉取网络数据
            mPresenter.getFriendsList(authorities);
            return;
        }

        mStateView.showContent();//显示内容
        mPresenter.getFriendsList(authorities);
    }


    @Override
    public void onGetFriendsSuccess(ResultList response) {
        KLog.e(new Gson().toJson(response));
        if (ListUtils.isEmpty(response.getList())) {
            //获取不到数据,显示空布局
            mStateView.showEmpty();
            return;
        }
        mStateView.showContent();//显示内容
        KLog.e("我是有数据的");
        momentsList.clear();
        momentsList.addAll(response.getList());
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
}
