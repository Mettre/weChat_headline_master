package com.chaychan.news.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.ForgetPasswordPresenter;
import com.chaychan.news.utils.LoginUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.ISendRequestListener;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import flyn.Eyes;

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

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_modif_password;
    }

    @Override
    public void initView() {
        mTvAuthor.setText("修改密码");
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
    }

    public static void startActivity(AppCompatActivity mContext) {
        Intent intent = new Intent(mContext, ModifyPasswordActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void initListener() {
        submit_btn.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                if (LoginUtils.ModifyPassword(oldPassword, newPassword)) {
                    mPresenter.ModifyPasswordRequest(oldPassword.getText().toString(), newPassword.getText().toString());
                }
                break;
            case R.id.iv_back:
                finish();
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
