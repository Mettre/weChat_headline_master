package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.enum_.SmsTypeEnum;
import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.RegisterPresenter;
import com.chaychan.news.utils.CountdownControl;
import com.chaychan.news.utils.LoginUtils;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.ToastUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.ISendRequestListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import flyn.Eyes;

/**
 * Created by app on 2017/7/26.
 * 注册
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements View.OnClickListener, ISendRequestListener<LoginBean> {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.phone_num)
    EditText phone;

    @Bind(R.id.verification_code_num)
    EditText verificationCodeNum;

    @Bind(R.id.password)
    EditText password;

    @Bind(R.id.verification_code)
    TextView verificationCode;

    @Bind(R.id.register_btn)
    TextView registerBtn;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;


    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        mContext.startActivity(intent);
    }

    private void onListener() {
        registerBtn.setOnClickListener(this);
        verificationCode.setOnClickListener(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_register;
    }


    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setText("注册");
        onListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                if (LoginUtils.getInstance().RegisterVerification(phone, verificationCodeNum, password)) {
                    mPresenter.RegisterRequest(phone.getText().toString(), password.getText().toString(), verificationCodeNum.getText().toString());
                }
                break;
            case R.id.verification_code:
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    ToastUtils.showShortToastSafe("手机号码不能为空！");
                    return;
                } else if (!LoginUtils.isMobileNO(phone.getText().toString())) {
                    ToastUtils.showShortToastSafe("请输入正确的手机号码！");
                    return;
                }
                mPresenter.verificationRequest(phone.getText().toString(), SmsTypeEnum.REGISTER);
                break;
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestFirstSuccess(LoginBean response) {

        ToastUtils.showShortToast("注册成功", 200);
        EventBus.getDefault().post(new StartBrotherEvent(StartBrotherEvent.RECOMMENDEDUSER));
        finish();
    }


    @Override
    public void onRequestSecondSuccess(LoginBean response) {
        ToastUtils.showShortToast("验证码( " + response.getMessage() + " )", 200);
        CountdownControl.changeBtnGetCode(verificationCode, this);

    }

    @Override
    public void onError() {

    }
}
