package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.FindUserBean;
import com.chaychan.news.model.entity.FollowBean;
import com.chaychan.news.ui.adapter.FindUserAdapter;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.FindUserPresenter;
import com.chaychan.news.utils.DisplayUtils;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.FindUserListener;
import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.github.nukc.stateview.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import flyn.Eyes;

/**
 * 查找好友
 */
public class FindUserActivity extends BaseActivity<FindUserPresenter> implements View.OnClickListener, FindUserListener<FindUserBean> {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Bind(R.id.tip_view)
    TipView mTipView;

    @Bind(R.id.cancel_button)
    TextView cancelButton;

    @Bind(R.id.userId_edit)
    EditText userIdEdit;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private FindUserAdapter findUserAdapter;
    private List<FindUserBean> visitorBeanList = new ArrayList<>();


    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, FindUserActivity.class);
        mContext.startActivity(intent);
    }


    @Override
    public void initListener() {
        iv_back.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        findUserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                MomentsActivity.startActivity(visitorBeanList.get(i).getUserId(), (Context) mActivities);
            }
        });

        userIdEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(v.getText().toString())) {
                        mPresenter.getFindUserList(v.getText().toString());
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_find_user;
    }


    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setText("添加关注");

        mRvComment.setLayoutManager(new GridLayoutManager(this, 1));
        mRvComment.addItemDecoration(new DisplayUtils.SimpleDividerItemDecoration(1));

        findUserAdapter = new FindUserAdapter(this, R.layout.item_find_user, visitorBeanList);
        mRvComment.setAdapter(findUserAdapter);

    }


    @Override
    public void initData() {
        mStateView = StateView.inject(mFlContent);
        if (mStateView != null) {
            mStateView.setLoadingResource(R.layout.page_loading);
            mStateView.setRetryResource(R.layout.page_net_error);
        }
        mStateView.showLoading();
    }

    @Override
    protected FindUserPresenter createPresenter() {
        return new FindUserPresenter(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.cancel_button:
                userIdEdit.setText("");
                visitorBeanList.clear();
                findUserAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onGetRefreshListSuccess(List<FindUserBean> response) {
        Log.e("mettre::::   ","--------------22222----------------");
        if (ListUtils.isEmpty(response)) {
            //获取不到数据,显示空布局
            mStateView.showEmpty();
            return;
        }
        Log.e("mettre::::   ","--------------1111----------------");
        mStateView.showContent();//显示内容

        visitorBeanList.clear();
        visitorBeanList.addAll(response);
        findUserAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        mTipView.show();//弹出提示

        if (ListUtils.isEmpty(visitorBeanList)) {
            //如果一开始进入没有数据
            mStateView.showRetry();//显示重试的布局
        }
    }

    @Override
    public void addFollowSuccess() {

    }

    @Override
    public void onFollowedError() {

    }
}
