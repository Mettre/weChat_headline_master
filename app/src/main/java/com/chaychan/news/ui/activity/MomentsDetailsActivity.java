package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.news.R;
import com.chaychan.news.enum_.DynamicTypeEnum;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.MomentsDetailsEntity;
import com.chaychan.news.model.entity.Reply;
import com.chaychan.news.ui.adapter.MomentsDetailsAdapter;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.MomentsDetailsPresenter;
import com.chaychan.news.ui.view.MomentsDetailsHeaderView;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.MomentsDetailsEntityHelper;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.MomentsDetailsListener;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.github.nukc.stateview.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import flyn.Eyes;

/**
 * 说说详情
 */
public class MomentsDetailsActivity extends BaseActivity<MomentsDetailsPresenter> implements MomentsDetailsListener<MomentsDetailsEntity> {

    private String momentsId;
    public static final String MOMENTS_ID = "momentsId";

    private MomentsDetailsAdapter mCommentAdapter;
    protected MomentsDetailsHeaderView mHeaderView;

    private List<Reply> momentsList = new ArrayList<>();
    private Moments moments;
    private MomentsDetailsEntity momentsRecord;

    @Bind(R.id.iv_back)
    ImageView mIvBack;

    @Bind(R.id.fl_content)
    FrameLayout mFlContent;

    @Bind(R.id.rv_comment)
    PowerfulRecyclerView mRvComment;

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.editText_comment)
    EditText editText;

    private LinearLayoutManager layoutManager;
    private String replyParentId;

    public static void startAlineActivity(Moments moments, Context mContext) {
        Intent intent = new Intent(mContext, MomentsDetailsActivity.class);
        intent.putExtra(MomentsDetailsActivity.MOMENTS_ID, moments);
        mContext.startActivity(intent);
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_details_moments;
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色\
        SoftUtils.setupUI(this, groupView);
        layoutManager = (LinearLayoutManager) mRvComment.getLayoutManager();
        mRvComment.setLayoutManager(layoutManager);
    }

    @Override
    public void initData() {
        mStateView = StateView.inject(mFlContent);
        if (mStateView != null) {
            mStateView.setLoadingResource(R.layout.page_loading);
            mStateView.setRetryResource(R.layout.page_net_error);
        }

        mStateView.showLoading();
        Intent intent = getIntent();
        moments = (Moments) intent.getExtras().get(MomentsDetailsActivity.MOMENTS_ID);
        if (moments == null || TextUtils.isEmpty(moments.getMomentsId())) {
            return;
        }
        momentsId = moments.getMomentsId();
        mCommentAdapter = new MomentsDetailsAdapter(this, R.layout.item_moment_reply, momentsList);
        mRvComment.setAdapter(mCommentAdapter);
        mHeaderView = new MomentsDetailsHeaderView(this);
        mHeaderView.setDetail(moments);
        mCommentAdapter.addHeaderView(mHeaderView);
        mCommentAdapter.setEnableLoadMore(false);
        mCommentAdapter.setEmptyView(R.layout.pager_no_comment, mRvComment);
        mCommentAdapter.setHeaderAndEmpty(true);

        momentsRecord = MomentsDetailsEntityHelper.getLastNewsRecord(momentsId);
        if (momentsRecord == null) {
            //找不到记录，拉取网络数据
            momentsRecord = new MomentsDetailsEntity();
        } else {
            List<Reply> newsList = momentsRecord.getReplyList();
            if (newsList != null && newsList.size() > 0) {
                momentsList.addAll(newsList);//添加到集合中
                mCommentAdapter.notifyDataSetChanged();//刷新adapter
            }
            mStateView.showContent();//显示内容
        }
        mPresenter.getRefreshMomentsList(momentsId);
    }


    @Override
    public void initListener() {
        mCommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (momentsList.get(i).getOwn()) {
                    SoftUtils.hideSoftKeyboard(MomentsDetailsActivity.this);
                    new SweetAlertDialog(MomentsDetailsActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("提示")
                            .setContentText("确认删除该评论吗")
                            .setCancelText("取消")
                            .setConfirmText("确定")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    mPresenter.deleteReplyMoments(momentsList.get(i).getReplyId());

                                }
                            })
                            .show();
                } else {
                    SoftUtils.showSoftKeyboard(editText);
                }
                replyParentId = momentsList.get(i).getReplyId();
                editText.setText("");
                editText.setHint("回复" + momentsList.get(i).getUserName() + ":");
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("---", "搜索操作执行");
                    if (!TextUtils.isEmpty(v.getText().toString())) {
                        mPresenter.addReplyMoments(momentsId, v.getText().toString(), replyParentId, DynamicTypeEnum.MOMENTS);
                    }
                }
                return false;
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("---", "setOnClickListener");
                editText.setHint("评论");
                editText.setText("");
                replyParentId = "";
            }
        });
    }


    @Override
    protected MomentsDetailsPresenter createPresenter() {
        return new MomentsDetailsPresenter(this);
    }

    @Override
    public void onGetListSuccess(MomentsDetailsEntity response) {

        mStateView.showContent();//显示内容
        momentsList.clear();
        momentsList.addAll(response.getReplyList());
        mCommentAdapter.notifyDataSetChanged();
        //保存到数据库
        MomentsDetailsEntityHelper.save(momentsId, response.getMoments(), response.getReplyList());
    }

    @Override
    public void onAddReplySuccess(MomentsDetailsEntity response) {
        mPresenter.getRefreshMomentsList(momentsId);
//        UIUtils.showToast("评论成功");
        editText.setHint("评论");
        editText.setText("");
        replyParentId = "";
    }

    @Override
    public void onError() {
        if (ListUtils.isEmpty(momentsList)) {
            mStateView.showRetry();//显示重试的布局
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
