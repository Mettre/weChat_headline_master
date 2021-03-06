package com.chaychan.news.event;

import com.chaychan.news.model.entity.UserInfo;

/**
 * Created by Mettre on 2018/8/14.
 */

public class StartBrotherEvent {

    public final static int REFRESHTAGE = 1001;//登陆  刷新我的--个人信息网络请求
    public final static int REFRESHTAGEEDIT = 1002;//修改个人信息 刷新我的--个人信息
    public final static int LOUGINOUT = 1003;//退出  刷新我的--个人信息网络请求
    public final static int MOMENTSFRAGMENT = 1004;//个人信息发生变化后 刷新朋友圈页面

    public final static int RELEASEMOMENTS = 1005;//发完说说后刷新朋友圈

    public final static int RECOMMENDEDUSER = 1006;//首次登陆推荐用户

    public final static int REFRESHACCOUNT = 1007;//记账后刷新账目列表

    public UserInfo userBean;
    public int EventType;

    public StartBrotherEvent(int EventType) {
        this.EventType = EventType;
    }

    public StartBrotherEvent(int EventType, UserInfo userBean) {
        this.EventType = EventType;
        this.userBean = userBean;
    }
}
