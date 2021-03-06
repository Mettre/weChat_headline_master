package com.chaychan.news.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.entity.AccountStatistics;
import com.chaychan.news.model.entity.UserInfo;
import com.chaychan.news.ui.activity.FeedbackActivity;
import com.chaychan.news.ui.activity.FindUserActivity;
import com.chaychan.news.ui.activity.FollowActivity;
import com.chaychan.news.ui.activity.InformationActivity;
import com.chaychan.news.ui.activity.LoginActivity;
import com.chaychan.news.ui.activity.VisitorListActivity;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.presenter.UserInfoPresenter;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.view.IRequestListener;
import com.chaychan.news.view.IRequestMineListener;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author ChayChan
 * @description: 我的fragment
 * @date 2017/6/12  21:47
 */

public class MineFragment extends BaseFragment<UserInfoPresenter> implements View.OnClickListener, IRequestMineListener<UserInfo> {

    @Bind(R.id.ll_top)
    LinearLayout ll_top;

    @Bind(R.id.icon_imageView)
    CircleImageView circleImageView;

    @Bind(R.id.nickName_text)
    TextView nickName_text;

    @Bind(R.id.feedback_RelativeLayout)
    RelativeLayout feedback_RelativeLayout;

    @Bind(R.id.visitor_LinearLayout)
    LinearLayout visitorLinearLayout;

    @Bind(R.id.follow_LinearLayout)
    LinearLayout followLinearLayout;

    @Bind(R.id.find_friend_LinearLayout)
    LinearLayout findFriendLinearLayout;

    @Bind(R.id.account_num)
    TextView account_num;

    @Bind(R.id.day_num)
    TextView day_num;

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
        if (!MyApp.getInstances().NotLogged()) {
            KLog.i("获取个人信息接口请求");
            mPresenter.getUserInfo(MyApp.getInstances().getToken());
            mPresenter.getStatistics(MyApp.getInstances().getToken());
        }
    }

    @Override
    public void initListener() {
        ll_top.setOnClickListener(this);
        feedback_RelativeLayout.setOnClickListener(this);
        visitorLinearLayout.setOnClickListener(this);
        followLinearLayout.setOnClickListener(this);
        findFriendLinearLayout.setOnClickListener(this);
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
                mPresenter.getUserInfo(MyApp.getInstances().getToken());
                mPresenter.getStatistics(MyApp.getInstances().getToken());
            }
        } else if (event.EventType == StartBrotherEvent.REFRESHTAGEEDIT) {
            userBean = event.userBean;
            getInformation();
        } else if (event.EventType == StartBrotherEvent.LOUGINOUT) {
            userBean = null;
            account_num.setText("0");
            day_num.setText("0");
            getInformation();
        }
    }

    @Override
    public void onClick(View v) {
        if (MyApp.getInstances().NotLogged()) {
            LoginActivity.startLoginActivity(mActivity, true);
            return;
        }
        switch (v.getId()) {
            case R.id.ll_top:
                if (userBean != null) {
                    InformationActivity.startActivity(mActivity, userBean);
                } else {
                    LoginActivity.startLoginActivity(mActivity, true);
                }
                break;
            case R.id.feedback_RelativeLayout:
                FeedbackActivity.startActivity(mActivity);
                break;
            case R.id.visitor_LinearLayout:
                VisitorListActivity.startActivity(mActivity);
                break;
            case R.id.follow_LinearLayout:
                FollowActivity.startActivity(mActivity);
                break;
            case R.id.find_friend_LinearLayout:
                FindUserActivity.startActivity(mActivity);
                break;
        }
    }

    @Override
    public void onRequestFirstSuccess(UserInfo response) {
        userBean = response;
        getInformation();
    }

    @Override
    public void onRequestSecondSuccess(AccountStatistics response) {
        account_num.setText(response.getTotalAccountNum()+"");
        day_num.setText(response.getTotalAccountDay()+"");
    }


    /**
     * 加载个人信息
     */
    private void getInformation() {
        MyApp.getInstances().setUserInfo(userBean);
        EventBus.getDefault().post(new StartBrotherEvent(StartBrotherEvent.MOMENTSFRAGMENT, userBean));
        if (userBean == null) {
            GlideUtils.loadRound(mActivity, null, circleImageView);
            nickName_text.setText("登录/注册");
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
