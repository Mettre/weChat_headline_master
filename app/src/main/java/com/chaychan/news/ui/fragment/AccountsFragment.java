package com.chaychan.news.ui.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.entity.AccountList;
import com.chaychan.news.model.entity.ResponseList;
import com.chaychan.news.ui.activity.RecommendedUsersActivity;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.presenter.AccountsPresenter;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.IFriendsListener;
import com.chaychan.uikit.TipView;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import flyn.Eyes;

public class AccountsFragment extends BaseFragment<AccountsPresenter> implements IFriendsListener<ResponseList<AccountList>> {

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.tip_view)
    TipView mTipView;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.exListView)
    ExpandableListView exListView;

    @Bind(R.id.shouru_text)
    TextView shouru_text;

    @Bind(R.id.zhichu_text)
    TextView zhichu_text;

    @Bind(R.id.year_text)
    TextView year_text;

    @Bind(R.id.month_text)
    TextView month_text;

    private String year = "2020";
    private String month = "06";

    private AccountsAdapter accountsAdapter;
    private List<AccountList> list;

    @Override
    protected AccountsPresenter createPresenter() {
        return new AccountsPresenter(this);
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_accounts;
    }

    @Override
    public void initView(View rootView) {
        iv_back.setVisibility(View.GONE);
        mTvAuthor.setText("我的记账本");
        Eyes.setStatusBarColor(mActivity, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色


    }

    private void initEvents() {
        accountsAdapter = new AccountsAdapter(list, mActivity);
        exListView.setAdapter(accountsAdapter);

        for (int i = 0; i < accountsAdapter.getGroupCount(); i++) {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        KLog.i("EventBus接受");
        if (event.EventType == StartBrotherEvent.REFRESHTAGE) {
            mPresenter.getMonthBillList(year, month);
        } else if (event.EventType == StartBrotherEvent.LOUGINOUT) {
            list.clear();
            accountsAdapter.notifyDataSetChanged();
            shouru_text.setText("0.00");
            zhichu_text.setText("0.00");
        }
    }


    @Override
    public void initData() {
        list = new ArrayList<>();
        exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {
        mStateView.showLoading();
        mStateView.showContent();//显示内容
        mPresenter.getMonthBillList(year, month);
    }


    @Override
    public void onGetFriendsSuccess(ResponseList<AccountList> response) {
        mStateView.showContent();//显示内容
        list = response.getData();
        initEvents();
        year_text.setText(year + "年");
        month_text.setText(month + "月");
        zhichu_text.setText(response.getExpenditure() + "");
        shouru_text.setText(response.getIncome() + "");
    }

    @Override
    public void onError() {
        mTipView.show();//弹出提示

        if (ListUtils.isEmpty(list)) {
            //如果一开始进入没有数据
            mStateView.showRetry();//显示重试的布局
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(AccountsFragment.this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterEventBus(AccountsFragment.this);
    }


    public class AccountsAdapter extends BaseExpandableListAdapter {

        private List<AccountList> groups;
        private Context context;

        /**
         * 构造函数
         */
        public AccountsAdapter(List<AccountList> groups, Context context) {
            super();
            this.groups = groups;
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return groups.get(groupPosition).getAccountBeans().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return groups.get(groupPosition).getAccountBeans().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder gholder;
            if (convertView == null) {
                gholder = new GroupHolder();
                convertView = View.inflate(context, R.layout.item_account_group, null);
                gholder.time_text = (TextView) convertView.findViewById(R.id.time_text);
                gholder.content_text = (TextView) convertView.findViewById(R.id.content_text);
                convertView.setTag(gholder);
            } else {
                gholder = (GroupHolder) convertView.getTag();
            }
            final AccountList group = (AccountList) getGroup(groupPosition);
            if (group != null) {
                gholder.time_text.setText(group.getRecordDay());
                gholder.content_text.setText("收入" + group.getIncomeDay() + "  支出" + group.getExpenditureDay());
            }
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final ChildHolder cholder;
            if (convertView == null) {
                cholder = new ChildHolder();
                View view = View.inflate(context, R.layout.item_account_child, null);
                convertView = view;

                cholder.title_text = (TextView) convertView.findViewById(R.id.title_text);
                cholder.content_text = (TextView) convertView.findViewById(R.id.account_text);
                convertView.setTag(cholder);
            } else {
                // convertView = childrenMap.get(groupPosition);
                cholder = (ChildHolder) convertView.getTag();
            }
            final AccountList.ListBean goods = (AccountList.ListBean) getChild(groupPosition, childPosition);

            if (goods != null) {
                cholder.title_text.setText(goods.getTitle());
                cholder.content_text.setText((goods.getType() == 0 ? "-" : "+") + goods.getAmount());
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }


        /**
         * 组元素绑定器
         */
        private class GroupHolder {
            private TextView content_text;
            private TextView time_text;
        }

        /**
         * 子元素绑定器
         */
        private class ChildHolder {

            TextView title_text;
            TextView content_text;
        }
    }
}
