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
import com.chaychan.news.model.entity.RecommendeBean;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.base.BaseActivity;
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
 * 推荐用户
 */
public class RecommendedUsersActivity extends BaseActivity<FindUserPresenter> implements View.OnClickListener, FindUserListener<ResultList<RecommendeBean>> {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    private RecommendedAdapter recommendedAdapter;
    private List<RecommendeBean> followBeanList = new ArrayList<>();

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    private int position = -1;

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, RecommendedUsersActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_recommended_users;
    }

    @Override
    public void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected FindUserPresenter createPresenter() {
        return new FindUserPresenter(this);
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setText("推荐用户");
    }

    @Override
    public void initData() {

        recommendedAdapter = new RecommendedAdapter(this, R.layout.item_recommended, followBeanList);
        mRvComment.setLayoutManager(new GridLayoutManager(this, 2));
        mRvComment.setAdapter(recommendedAdapter);

        recommendedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MomentsActivity.startActivity(followBeanList.get(i).getUserId(), RecommendedUsersActivity.this, null);
            }
        });

//        recommendedAdapter.setEmptyView(R.layout.pager_no_faceback);
        mPresenter.recommendedRequest();
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
    public void onGetRefreshListSuccess(ResultList<RecommendeBean> response) {
        followBeanList = response.getData();
        recommendedAdapter.setNewData(followBeanList);
    }

    @Override
    public void onError() {

    }

    @Override
    public void addFollowSuccess() {
        if (position == -1) {
            return;
        }
        followBeanList.get(position).setHasFollow(!followBeanList.get(position).getHasFollow());
        recommendedAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelFollowSuccess() {
        if (position == -1) {
            return;
        }
        followBeanList.get(position).setHasFollow(!followBeanList.get(position).getHasFollow());
        recommendedAdapter.notifyDataSetChanged();
    }

    public class RecommendedAdapter extends BaseQuickAdapter<RecommendeBean, BaseViewHolder> {
        private Context mContext;

        public RecommendedAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<RecommendeBean> data) {
            super(layoutResId, data);
            mContext = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, RecommendeBean followBean) {
            GlideUtils.loadRound(mContext, followBean.getHeadAvatar(), helper.getView(R.id.icon_head));
            helper.setText(R.id.user_name, followBean.getUserName());
            helper.setOnClickListener(R.id.allow_text, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = helper.getAdapterPosition();
                    if (!followBean.getHasFollow()) {
                        mPresenter.addFollowRequest(followBean.getUserId());
                    } else {
                        mPresenter.cancelFollowRequest(followBean.getUserId());
                    }
                }
            });
            helper.getView(R.id.allow_text).setEnabled(!followBean.getHasFollow());
            if (followBean.getHasFollow()) {
                helper.setText(R.id.allow_text, "已关注");
                helper.setTextColor(R.id.allow_text, mContext.getResources().getColor(R.color.gray_light));
            } else {
                helper.setText(R.id.allow_text, "添加关注");
                helper.setTextColor(R.id.allow_text, mContext.getResources().getColor(R.color.white));
            }
        }
    }
}
