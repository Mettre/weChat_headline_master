package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.FeedbackPresenter;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.IRequestListener;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import flyn.Eyes;

public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements View.OnClickListener, IRequestListener<ResultResponse> {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.right_btn)
    TextView right_btn;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.nickname_edt)
    EditText editText;

    @Bind(R.id.feedback_list_btn)
    Button feedback_list_btn;

    private String faceBackContent;

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, FeedbackActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected FeedbackPresenter createPresenter() {
        return new FeedbackPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setText("意见反馈");
    }


    @Override
    public void initListener() {
        right_btn.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        feedback_list_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.right_btn:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    UIUtils.showToast("请输入反馈内容");
                    return;
                }
                faceBackContent = editText.getText().toString();
                mPresenter.addFeedbackRequest(faceBackContent);
                break;
            case R.id.feedback_list_btn:
                FeedbackListActivity.startActivity(FeedbackActivity.this);
                break;
        }
    }

    @Override
    public void onRequestFirstSuccess(ResultResponse response) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("")
                .setContentText("反馈内容已提交，系统会尽快处理！")
                .setConfirmText("关闭")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onError() {

    }
}
