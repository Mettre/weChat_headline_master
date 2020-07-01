package com.chaychan.news.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.entity.AccountClassification;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.AddAccountPresenter;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.TabEntity;
import com.chaychan.news.utils.ToastUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.FindUserListener;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import flyn.Eyes;

/**
 * 添加记账
 */
public class AddAccountActivity extends BaseActivity<AddAccountPresenter> implements View.OnClickListener, FindUserListener<ResultList<AccountClassification>> {

    @Bind(R.id.tabLayout)
    CommonTabLayout tableLayout;

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private RecommendedAdapter recommendedAdapter;
    private List<AccountClassification> followBeanList = new ArrayList<>();

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.text_num0)
    TextView text_num0;

    @Bind(R.id.text_num1)
    TextView text_num1;

    @Bind(R.id.text_num2)
    TextView text_num2;

    @Bind(R.id.text_num3)
    TextView text_num3;

    @Bind(R.id.text_num4)
    TextView text_num4;

    @Bind(R.id.text_num5)
    TextView text_num5;

    @Bind(R.id.text_num6)
    TextView text_num6;

    @Bind(R.id.text_num7)
    TextView text_num7;

    @Bind(R.id.text_num8)
    TextView text_num8;

    @Bind(R.id.text_num9)
    TextView text_num9;

    @Bind(R.id.text_numx)
    TextView text_numx;

    @Bind(R.id.text_day)
    TextView text_day;

    @Bind(R.id.text_add)
    TextView text_add;

    @Bind(R.id.text_del)
    TextView text_del;

    @Bind(R.id.text_dian)
    TextView text_dian;

    @Bind(R.id.complete_btn)
    TextView complete_btn;

    @Bind(R.id.content_edit)
    EditText content_edit;

    @Bind(R.id.money_text)
    TextView money_text;

    private String title;
    private String classification;
    private Integer type = 0;
    private String recordDay = "2020-06-30";
    private BigDecimal amount = new BigDecimal("0.00");

    private ArrayList<CustomTabEntity> mTabEntities;
    private String[] mTitles = new String[]{"支出", "收入"};
    private int position = -1;

    public static void startActivity(Activity mContext) {
        Intent intent = new Intent(mContext, AddAccountActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_account;
    }

    @Override
    public void initListener() {
        iv_back.setOnClickListener(this);

        text_num0.setOnClickListener(this);
        text_num1.setOnClickListener(this);
        text_num2.setOnClickListener(this);
        text_num3.setOnClickListener(this);
        text_num4.setOnClickListener(this);
        text_num5.setOnClickListener(this);
        text_num6.setOnClickListener(this);
        text_num7.setOnClickListener(this);
        text_num8.setOnClickListener(this);
        text_num9.setOnClickListener(this);
        text_numx.setOnClickListener(this);
        text_day.setOnClickListener(this);
        text_add.setOnClickListener(this);
        text_del.setOnClickListener(this);
        text_dian.setOnClickListener(this);
        complete_btn.setOnClickListener(this);
    }

    @Override
    protected AddAccountPresenter createPresenter() {
        return new AddAccountPresenter(this);
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.yellow));//设置状态栏的颜色为灰色
        SoftUtils.setupUI(this, groupView);

        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        tl_2();
    }

    private void tl_2() {
        tableLayout.setTabData(mTabEntities);
        tableLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                type = position;
                mPresenter.classificationList(type);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    public void initData() {

        recommendedAdapter = new RecommendedAdapter(this, R.layout.item_classification_account, followBeanList);
        mRvComment.setLayoutManager(new GridLayoutManager(this, 4));
        mRvComment.setAdapter(recommendedAdapter);

        recommendedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                classification = followBeanList.get(i).getId().toString();
                for (AccountClassification accountClassification : followBeanList) {
                    accountClassification.setHasChoice(false);
                }
                followBeanList.get(i).setHasChoice(true);
                recommendedAdapter.notifyDataSetChanged();
            }
        });

        mPresenter.classificationList(type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.text_num0:
                getNumDecimal(0);
                break;
            case R.id.text_num1:
                getNumDecimal(1);
                break;
            case R.id.text_num2:
                getNumDecimal(2);
                break;
            case R.id.text_num3:
                getNumDecimal(3);
                break;
            case R.id.text_num4:
                getNumDecimal(4);
                break;
            case R.id.text_num5:
                getNumDecimal(5);
                break;
            case R.id.text_num6:
                getNumDecimal(6);
                break;
            case R.id.text_num7:
                getNumDecimal(7);
                break;
            case R.id.text_num8:
                getNumDecimal(8);
                break;
            case R.id.text_num9:
                getNumDecimal(9);
                break;
            case R.id.text_numx:
                delNumDecimal();
                break;
            case R.id.text_dian:
                getNumDecimal(".");
                break;
            case R.id.text_add:

                break;
            case R.id.text_del:

                break;
            case R.id.complete_btn:
                title = content_edit.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.showShortToast("备注不能为空");
                    return;
                }
                if (amount.compareTo(BigDecimal.ZERO) == 0) {
                    ToastUtils.showShortToast("金额不能为0");
                    return;
                }
                if (TextUtils.isEmpty(classification)) {
                    ToastUtils.showShortToast("请选择账目分类");
                    return;
                }
                if (TextUtils.isEmpty(recordDay)) {
                    ToastUtils.showShortToast("日期不能为空");
                    return;
                }

                mPresenter.addAccount(title, amount, classification, type, recordDay);
                break;
            case R.id.text_day:

                break;
        }
    }

    private void getNumDecimal(String num) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            money_text.setText("0" + num);
        } else {
            money_text.setText(amount.toString() + num);
        }
        amount = new BigDecimal(money_text.getText().toString());
    }

    private void getNumDecimal(int num) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            if (!"0.00".equals(money_text.getText().toString())) {
                money_text.setText(money_text.getText().toString() + num);
            } else {
                if (num == 0) {
                    return;
                }
                money_text.setText("" + num);
            }
        } else {
            money_text.setText(money_text.getText().toString() + "" + num);
        }
        amount = new BigDecimal(money_text.getText().toString());
    }

    private void delNumDecimal() {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            amount = new BigDecimal("0.00");
            money_text.setText(amount + "");
            return;
        }
        String str = money_text.getText().toString();
        if (TextUtils.isEmpty(str)) {
            return;
        }

        str = str.substring(0, str.length() - 1);
        if (TextUtils.isEmpty(str)) {
            money_text.setText("0.00");
            amount = new BigDecimal("0.00");
        } else {
            amount = new BigDecimal(money_text.getText().toString());
            money_text.setText(amount + "");
        }
    }

    @Override
    public void onGetRefreshListSuccess(ResultList<AccountClassification> response) {
        followBeanList = response.getData();
        recommendedAdapter.setNewData(followBeanList);
    }

    @Override
    public void onError() {

    }

    @Override
    public void addFollowSuccess() {
        EventBus.getDefault().post(new StartBrotherEvent(StartBrotherEvent.REFRESHTAGE));
        finish();
    }

    @Override
    public void cancelFollowSuccess() {

    }


    public class RecommendedAdapter extends BaseQuickAdapter<AccountClassification, BaseViewHolder> {

        public RecommendedAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<AccountClassification> data) {
            super(layoutResId, data);
            mContext = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, AccountClassification followBean) {
            helper.setText(R.id.classification_title, followBean.getClassificationTitle());
            if (!followBean.getHasChoice()) {
                helper.setBackgroundRes(R.id.classification_bg, R.drawable.bg_round_gray);
            } else {
                helper.setBackgroundRes(R.id.classification_bg, R.drawable.bg_round_yellow);
            }

        }
    }
}
