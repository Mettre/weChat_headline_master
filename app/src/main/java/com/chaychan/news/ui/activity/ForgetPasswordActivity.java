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
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.ForgetPasswordPresenter;
import com.chaychan.news.utils.CountdownControl;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.ToastUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.ISendRequestListener;

import butterknife.Bind;
import flyn.Eyes;

import static com.chaychan.news.utils.LoginUtils.isMobileNO;

/**
 * Created by app on 2017/7/26.
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements View.OnClickListener, ISendRequestListener<Object> {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.verification_code_num)
    EditText verificationCodeNum;

    @Bind(R.id.new_password)
    EditText newPassword;

    @Bind(R.id.phone_num_text)
    EditText phoneNumText;

    @Bind(R.id.verification_code)
    TextView verificationCode;

    @Bind(R.id.submit_btn)
    TextView submit_btn;


    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, ForgetPasswordActivity.class);
        mContext.startActivity(intent);
    }


    private void onListener() {
        submit_btn.setOnClickListener(this);
        verificationCode.setOnClickListener(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_forget_password;
    }


    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setText("忘记密码");
        onListener();
    }

    @Override
    protected ForgetPasswordPresenter createPresenter() {
        return new ForgetPasswordPresenter(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                SoftUtils.hideSoftKeyboard(this);
                mPresenter.ForgetPasswordRequest(phoneNumText.getText().toString(), newPassword.getText().toString(),verificationCodeNum.getText().toString());
                break;
            case R.id.verification_code:
                if (TextUtils.isEmpty(phoneNumText.getText().toString())) {
                    ToastUtils.showShortToastSafe("手机号码不能为空！");
                    return;
                } else if (!isMobileNO(phoneNumText.getText().toString())) {
                    ToastUtils.showShortToastSafe("请输入正确的手机号码！");
                    return;
                }
                mPresenter.verificationRequest(phoneNumText.getText().toString(), SmsTypeEnum.FORGET_PASSWORD);
                break;
        }
    }

    @Override
    public void onRequestFirstSuccess(Object response) {
        ToastUtils.showShortToast("密码修改成功", 200);
    }

    @Override
    public void onRequestSecondSuccess(Object response) {
        ToastUtils.showShortToast("验证码已发送到手机，请注意查收", 200);
        CountdownControl.changeBtnGetCode(verificationCode, this);
    }


    @Override
    public void onError() {

    }
}
