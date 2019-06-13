package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.LoginPresenter;
import com.chaychan.news.utils.LoginUtils;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.ui.view.ClearEditText;
import com.chaychan.news.view.IRequestListener;

import butterknife.Bind;
import butterknife.OnClick;
import flyn.Eyes;

/**
 * Created by Mettre on 2018/8/15.
 * 登录
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, IRequestListener<LoginBean> {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.login_btn)
    TextView login_btn;

    @Bind(R.id.register_btn)
    TextView register_btn;

    @Bind(R.id.forgot_password_btn)
    TextView forgot_password_btn;

    @Bind(R.id.phone_num)
    ClearEditText phoneEdt;
    private String phoneNum;

    @Bind(R.id.password)
    EditText passwordEdt;


    public static void startLoginActivity(Context mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }


    private void inListener() {
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forgot_password_btn.setOnClickListener(this);
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        SoftUtils.setupUI(this, groupView);
        inListener();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (LoginUtils.loginCheck(phoneEdt, passwordEdt)) {
                    phoneNum = phoneEdt.getText().toString();
                    mPresenter.LoginRequest(phoneNum, passwordEdt.getText().toString());
                }
                break;
        }
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRequestFirstSuccess(LoginBean response) {
        LoginUtils.loginSaveToken(phoneNum, response.getAccess_token());
        finish();
    }

    @Override
    public void onError() {

    }
}
