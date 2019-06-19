package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.enum_.GenderEnum;
import com.chaychan.news.enum_.SmsTypeEnum;
import com.chaychan.news.model.entity.UserInfo;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.EditInformationPresenter;
import com.chaychan.news.ui.presenter.LoginPresenter;
import com.chaychan.news.utils.LoginUtils;
import com.chaychan.news.utils.ToastUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.IRequestListener;

import java.util.List;

import butterknife.Bind;
import flyn.Eyes;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by app on 2017/10/20.
 * 修改信息
 */
public class EditNickNameActivity extends BaseActivity<EditInformationPresenter> implements View.OnClickListener, EasyPermissions.PermissionCallbacks, IRequestListener<ResultResponse> {

    public final static int NICKNAME = 1;
    public final static int SEX = 2;
    public final static int EMALL = 3;
    public final static int AGE = 4;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.nickname_edt)
    EditText nicknameEdt;

    @Bind(R.id.group_gender)
    RadioGroup radioGroup;

    @Bind(R.id.len_num)
    TextView len_num;

    @Bind(R.id.prompt_text)
    TextView promptText;

    @Bind(R.id.right_btn)
    TextView right_btn;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    /**
     * 个人信息
     */
    private UserInfo userBean;
    private String nickName;
    private GenderEnum gender;
    private String e_mall;
    private Integer age;
    private int type;//1:昵称 2:性别 3：e-mall 4：年龄

    public static void startActivity(AppCompatActivity mContext, UserInfo userBean, int requestCode) {
        Intent intent = new Intent(mContext, EditNickNameActivity.class);
        intent.putExtra("userBean", userBean);
        intent.putExtra("requestCode", requestCode);
        mContext.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {
        userBean = (UserInfo) getIntent().getSerializableExtra("userBean");
        type = getIntent().getIntExtra("requestCode", 0);
        nickName = userBean.getUserName();
        age = userBean.getAge();
        gender = userBean.getGender();
        e_mall = userBean.getE_mall();
        mTvAuthor.setText("修改信息");
        switch (type) {
            case NICKNAME:
                mTvAuthor.setText("编辑昵称");
                break;
            case SEX:
                mTvAuthor.setText("编辑性别");
                break;
        }
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        switch (type) {
            case NICKNAME:
                nicknameEdt.setText(nickName);
                nicknameEdt.setHint("请输入昵称");
                promptText.setText("限制4-10位字符");
                LimitMaxLine(10);
                if (!TextUtils.isEmpty(nickName)) {
                    len_num.setText(nickName.length() + "/" + 10);
                }
                break;
            case SEX:
                radioGroup.setVisibility(View.VISIBLE);
                nicknameEdt.setVisibility(View.GONE);
                promptText.setVisibility(View.GONE);
                len_num.setVisibility(View.GONE);
                if (gender != null) {
                    switch (gender) {
                        case MAN:
                            radioGroup.check(R.id.gender_male);
                            break;
                        case WOMAN:
                            radioGroup.check(R.id.gender_female);
                            break;
                    }
                }
                break;
            case 3:
                nicknameEdt.setText(e_mall);
                nicknameEdt.setHint("请输入您的邮箱");
                promptText.setText("请输入电子邮箱地址");
//                nicknameEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                break;
        }
    }

    @Override
    public void initListener() {
        right_btn.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    protected EditInformationPresenter createPresenter() {
        return new EditInformationPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_edit_nick_name;
    }

    private void LimitMaxLine(final int maxLen) {
        len_num.setText("0/" + maxLen);
        nicknameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable editable = nicknameEdt.getText();
                int len = editable.length();

                len_num.setText(len + "/" + maxLen);
                if (len > maxLen) {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, maxLen);
                    nicknameEdt.setText(newStr);
                    editable = nicknameEdt.getText();

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
                if (type == NICKNAME) {
                    if (TextUtils.isEmpty(nicknameEdt.getText().toString())) {
                        ToastUtils.showCenterToast("昵称不能为空", 200);
                        return;
                    }
                    nickName = nicknameEdt.getText().toString();
                    mPresenter.EditInformationRequest(nickName, null, null, null);
                } else if (type == SEX) {
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.gender_male:
                            mPresenter.EditInformationRequest(null, GenderEnum.MAN, null, null);
                            break;
                        case R.id.gender_female:
                            mPresenter.EditInformationRequest(null, GenderEnum.WOMAN, null, null);
                            break;
                    }
                } else if (type == EMALL) {
                    if (TextUtils.isEmpty(nicknameEdt.getText().toString()) || !LoginUtils.checkEmail(nicknameEdt.getText().toString())) {
                        ToastUtils.showCenterToast("请输入正确的e_mall", 200);
                        return;
                    }
                    e_mall = nicknameEdt.getText().toString();
                    mPresenter.EditInformationRequest(null, null, null, e_mall);
                } else if (type == AGE) {
                    if (TextUtils.isEmpty(nicknameEdt.getText().toString())) {
                        ToastUtils.showCenterToast("年龄不能为空", 200);
                        return;
                    }
                    age = Integer.parseInt(nicknameEdt.getText().toString());
                    mPresenter.EditInformationRequest(null, null, age, null);
                }
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestFirstSuccess(ResultResponse response) {
        if (type == SEX) {
            Intent intent = new Intent();
            intent.putExtra("inputEdit", type);
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.gender_male:
                    intent.putExtra("inputEdit", GenderEnum.MAN.gender);
                    break;
                case R.id.gender_female:
                    intent.putExtra("inputEdit", GenderEnum.WOMAN.gender);
                    break;
            }
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Intent intent = new Intent();
            intent.putExtra("type", type);
            intent.putExtra("inputEdit", nicknameEdt.getText().toString());
            setResult(RESULT_OK, intent);
            finish();

        }
    }

    @Override
    public void onError() {

    }
}
