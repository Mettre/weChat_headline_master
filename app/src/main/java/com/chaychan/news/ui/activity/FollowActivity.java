package com.chaychan.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaychan.news.R;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.ui.fragment.FansFragment;
import com.chaychan.news.ui.fragment.MyFollowFragment;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.TabEntity;
import com.chaychan.news.utils.UIUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;
import flyn.Eyes;

/**
 * 关注
 */
public class FollowActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.tabLayout)
    CommonTabLayout tableLayout;

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    private ArrayList<Fragment> mFragments2;
    private ArrayList<CustomTabEntity> mTabEntities;
    private String[] mTitles = new String[]{"我的关注", "我的粉丝"};

    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, FollowActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_follow;
    }

    @Override
    public void initListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色\
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setText("关注");
    }

    @Override
    public void initData() {
        mFragments2 = new ArrayList<>();
        mFragments2.add(MyFollowFragment.newInstance());
        mFragments2.add(FansFragment.newInstance());

        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tl_2();
    }

    private void tl_2() {
        tableLayout.setTabData(mTabEntities);
        tableLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tableLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments2.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments2.get(position);
        }
    }
}
