package com.chaychan.news.ui.fragment;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chaychan.news.R;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.app.base.BaseApp;
import com.chaychan.news.model.entity.UserInfo;
import com.chaychan.news.ui.activity.InformationActivity;
import com.chaychan.news.ui.activity.LoginActivity;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.ui.presenter.LoginPresenter;
import com.chaychan.news.ui.presenter.UserInfoPresenter;
import com.chaychan.news.utils.ToastUtils;
import com.chaychan.news.view.IRequestListener;
import com.socks.library.KLog;

import butterknife.Bind;

/**
 * @author ChayChan
 * @description: 我的fragment
 * @date 2017/6/12  21:47
 */

public class MineFragment extends BaseFragment<UserInfoPresenter> implements View.OnClickListener, IRequestListener<UserInfo> {

    @Bind(R.id.ll_top)
    LinearLayout ll_top;

    private UserInfo userBean;

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View rootView) {
        KLog.i("initView");
    }

    @Override
    public void initData() {
        KLog.i("initData");
    }

    @Override
    public void initListener() {
        ll_top.setOnClickListener(this);
    }


    @Override
    public void loadData() {
        KLog.i("loadData");
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        Log.e("mettre:   ", isVisible + " - " + MyApp.getInstances().getMineUi() + " - " + !MyApp.getInstances().NotLogged());

        //登录获取用户信息
        if (isVisible && MyApp.getInstances().getMineUi() && !MyApp.getInstances().NotLogged()) {
            ToastUtils.showShortToast("刷新用户数据");
            mPresenter.getUserInfo(MyApp.getInstances().getToken());
            MyApp.getInstances().setMineUi(false);
        } else {

        }

    }

    @Override
    public void onClick(View v) {
        if (MyApp.getInstances().NotLogged()) {
            LoginActivity.startLoginActivity(mActivity);
            return;
        }
        switch (v.getId()) {
            case R.id.ll_top:
                if (userBean != null) {
                    InformationActivity.startActivity(mActivity, userBean);
                }
                break;
        }
    }

    @Override
    public void onRequestFirstSuccess(UserInfo response) {
        userBean = response;
    }

    @Override
    public void onError() {

    }
}
