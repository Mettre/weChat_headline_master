package com.chaychan.news.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.ForgetPasswordPresenter;
import com.chaychan.news.utils.LoginUtils;
import com.chaychan.news.view.ISendRequestListener;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ModifyPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements View.OnClickListener, ISendRequestListener<ResultResponse> {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.new_password)
    EditText newPassword;

    @Bind(R.id.old_password)
    EditText oldPassword;

    @Bind(R.id.submit_btn)
    TextView submit_btn;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_modif_password;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        submit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                if (LoginUtils.ModifyPassword(oldPassword, newPassword)) {
                    mPresenter.ModifyPasswordRequest(oldPassword.getText().toString(), newPassword.getText().toString());
                }
                break;
        }
    }

    @Override
    protected ForgetPasswordPresenter createPresenter() {
        return new ForgetPasswordPresenter(this);
    }


    @Override
    public void onRequestFirstSuccess(ResultResponse response) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("")
                .setContentText("密码修改成功")
                .setConfirmText("关闭")
                .show();
    }

    @Override
    public void onRequestSecondSuccess(ResultResponse response) {

    }

    @Override
    public void onError() {

    }
}
