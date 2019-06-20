package com.chaychan.news.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.entity.UserInfo;
import com.chaychan.news.ui.activity.InformationActivity;
import com.chaychan.news.ui.activity.LoginActivity;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.presenter.UserInfoPresenter;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.ToastUtils;
import com.chaychan.news.view.IRequestListener;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author ChayChan
 * @description: 我的fragment
 * @date 2017/6/12  21:47
 */

public class MineFragment extends BaseFragment<UserInfoPresenter> implements View.OnClickListener, IRequestListener<UserInfo> {

    @Bind(R.id.ll_top)
    LinearLayout ll_top;

    @Bind(R.id.icon_imageView)
    CircleImageView circleImageView;

    @Bind(R.id.nickName_text)
    TextView nickName_text;

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
        if (!MyApp.getInstances().NotLogged()) {
            KLog.i("获取个人信息接口请求");
            mPresenter.getUserInfo(MyApp.getInstances().getToken());
        }
    }

    @Override
    public void initListener() {
        ll_top.setOnClickListener(this);
    }


    @Override
    public void loadData() {
        KLog.i("loadData");
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        KLog.i("EventBus接受");
        if (event.EventType == StartBrotherEvent.REFRESHTAGE) {
            //登录获取用户信息
            if (!MyApp.getInstances().NotLogged()) {
                ToastUtils.showShortToast("登录成功刷新用户数据");
                mPresenter.getUserInfo(MyApp.getInstances().getToken());
            }
        } else if (event.EventType == StartBrotherEvent.REFRESHTAGEEDIT) {
            userBean = event.userBean;
            getInformation();
        } else if (event.EventType == StartBrotherEvent.LOUGINOUT) {
            ToastUtils.showShortToast("退出登录清除用户信息");
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
                } else {
                    ToastUtils.showCenterToast("个人信息为空", 200);
                    LoginActivity.startLoginActivity(mActivity);

                }
                break;
        }
    }

    @Override
    public void onRequestFirstSuccess(UserInfo response) {
        userBean = response;
        getInformation();
    }


    /**
     * 加载个人信息
     */
    private void getInformation() {
        if (userBean == null) {
            return;
        }
        GlideUtils.loadRound(mActivity, userBean.getHeadAvatar(), circleImageView);
        nickName_text.setText(userBean.getUserName());
    }


    @Override
    public void onError() {

    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(MineFragment.this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterEventBus(MineFragment.this);
    }
}
