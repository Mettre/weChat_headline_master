package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.enum_.MomentsEnum;
import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.entity.ReleaseMoments;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.ReleaseMomentsPresenter;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.ToastUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.MomentsReleaseListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import flyn.Eyes;


/**
 * 发布说说
 */
public class ReleaseMomentsActivity extends BaseActivity<ReleaseMomentsPresenter> implements View.OnClickListener, MomentsReleaseListener<ResultResponse> {

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

    @Bind(R.id.len_num)
    TextView len_num;

    private List<File> fileList = new ArrayList<>();

    private int maxNum = 240;
    private String faceBackContent;
    private MomentsEnum momentsType;
    private ReleaseMoments releaseMoments = new ReleaseMoments();

    public static void startActivity(Context mContext, MomentsEnum momentsType) {
        Intent intent = new Intent(mContext, ReleaseMomentsActivity.class);
        intent.putExtra("momentsType", momentsType);
        mContext.startActivity(intent);
    }

    @Override
    protected ReleaseMomentsPresenter createPresenter() {
        return new ReleaseMomentsPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_release_moments;
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        momentsType = (MomentsEnum) getIntent().getSerializableExtra("momentsType");
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setVisibility(View.GONE);
        right_btn.setText("发布");
    }


    @Override
    public void initListener() {
        right_btn.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        len_num.setText("0/" + maxNum);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable editable = editText.getText();
                int len = editable.length();

                len_num.setText(len + "/" + maxNum);
                if (len > maxNum) {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, maxNum);
                    editText.setText(newStr);
                    editable = editText.getText();

                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.right_btn:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    UIUtils.showToast("说说内容不能为空");
                    return;
                }
                faceBackContent = editText.getText().toString();

                releaseMoments.setMomentsTitle(faceBackContent);
                releaseMoments.setMomentsType(momentsType);
                switch (momentsType) {
                    case PURE_TEXT:
                        mPresenter.releaseMoments(releaseMoments);
                        break;
                    case PHOTO:
                        mPresenter.uploadFileRequest(fileList);
                        break;
                }

                break;
        }
    }


    @Override
    public void onReleaseSuccess(ResultResponse response) {
        EventBus.getDefault().post(new StartBrotherEvent(StartBrotherEvent.RELEASEMOMENTS));
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("")
                .setContentText("发布成功")
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
    public void onUpFileSuccess(ResultResponse response) {
        releaseMoments.setMomentsImage(faceBackContent);
    }

    @Override
    public void onError() {

    }
}
