package com.chaychan.news.ui.activity;

import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.chaychan.news.R;
import com.chaychan.news.model.event.TabRefreshCompletedEvent;
import com.chaychan.news.ui.adapter.MainTabAdapter;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.base.BaseFragment;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.ui.fragment.AccountsFragment;
import com.chaychan.news.ui.fragment.FriendFragment;
import com.chaychan.news.ui.fragment.MomentsFragment;
import com.chaychan.news.ui.fragment.MineFragment;
import com.chaychan.uikit.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import flyn.Eyes;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseActivity {

    @Bind(R.id.vp_content)
    NoScrollViewPager mVpContent;

    @Bind(R.id.bottom_bar)
    BottomBarLayout mBottomBarLayout;

    private List<BaseFragment> mFragments;
    private MainTabAdapter mTabAdapter;

    private int[] mStatusColors = new int[]{
            R.color.color_D33D3C,
            R.color.color_BDBDBD,
            R.color.color_3333,
            R.color.color_3333
    };

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mFragments = new ArrayList<>(4);
        mFragments.add(new FriendFragment());
        mFragments.add(new MomentsFragment());
        mFragments.add(new AccountsFragment());
        mFragments.add(new MineFragment());
    }

    @Override
    public void initListener() {
        mTabAdapter = new MainTabAdapter(mFragments, getSupportFragmentManager());
        mVpContent.setAdapter(mTabAdapter);
        mVpContent.setOffscreenPageLimit(mFragments.size());
        mBottomBarLayout.setViewPager(mVpContent);
        //设置条目点击的监听
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {
                setStatusBarColor(position);//设置状态栏颜色

                JCVideoPlayer.releaseAllVideos();//底部页签切换或者是下拉刷新，释放资源

                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.mipmap.tab_micro_selected);//更换为原来的图标

                cancelTabLoading(bottomItem);//停止旋转动画
            }
        });
    }

    private void setStatusBarColor(int position) {
        if (position == 1 || position == 0 || position == 2 || position == 3) {
            //如果是我的页面，状态栏设置为透明状态栏
            Eyes.translucentStatusBar(MainActivity.this, true);
        } else {
            Eyes.setStatusBarColor(MainActivity.this, com.chaychan.news.utils.UIUtils.getColor(mStatusColors[position]));
        }
    }

    /**
     * 停止首页页签的旋转动画
     */
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView().getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshCompletedEvent(TabRefreshCompletedEvent event) {
        //接收到刷新完成的事件，取消旋转动画，更换底部首页页签图标
        BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);

        cancelTabLoading(bottomItem);//停止旋转动画

        bottomItem.setIconSelectedResourceId(R.mipmap.tab_micro_selected);//更换成首页原来图标
        bottomItem.setStatus(true);//刷新图标
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus(MainActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
