package com.chaychan.news.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.ForgetPasswordPresenter;
import com.chaychan.news.view.IRequestListener;

import butterknife.Bind;

public class ModifyPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements View.OnClickListener, IRequestListener<ResultResponse> {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.new_password)
    EditText newPassword;

    @Bind(R.id.submit_btn)
    TextView submit_btn;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_modif_password;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected ForgetPasswordPresenter createPresenter() {
        return null;
    }



    @Override
    public void onRequestFirstSuccess(ResultResponse response) {

    }

    @Override
    public void onError() {

    }
}
