package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.news.R;
import com.chaychan.news.model.entity.AccountClassification;
import com.chaychan.news.model.entity.RecommendeBean;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.AddAccountPresenter;
import com.chaychan.news.ui.presenter.FindUserPresenter;
import com.chaychan.news.utils.GlideUtils;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.FindUserListener;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import flyn.Eyes;

/**
 * 添加记账
 */
public class AddAccountActivity extends BaseActivity<AddAccountPresenter> implements View.OnClickListener, FindUserListener<ResultList<AccountClassification>> {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private RecommendedAdapter recommendedAdapter;
    private List<AccountClassification> followBeanList = new ArrayList<>();

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    private int position = -1;

    public static void startActivity(Context mContext) {
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
    }

    @Override
    protected AddAccountPresenter createPresenter() {
        return new AddAccountPresenter(this);
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setText("记账分类");
    }

    @Override
    public void initData() {

        recommendedAdapter = new RecommendedAdapter(this, R.layout.item_classification_account, followBeanList);
        mRvComment.setLayoutManager(new GridLayoutManager(this, 4));
        mRvComment.setAdapter(recommendedAdapter);

        recommendedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                for (AccountClassification accountClassification : followBeanList) {
                    accountClassification.setHasChoice(false);
                }
                followBeanList.get(i).setHasChoice(true);
                recommendedAdapter.notifyDataSetChanged();
            }
        });

        mPresenter.classificationList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
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
