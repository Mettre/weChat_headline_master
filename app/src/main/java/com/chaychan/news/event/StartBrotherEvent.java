package com.chaychan.news.event;


/**
 * Created by Mettre on 2018/8/14.
 */

public class StartBrotherEvent {

    public final static int REFRESHTAGE = 1001;//登陆/退出  刷新我的--个人信息

    public int EventType;

    public StartBrotherEvent(int EventType) {
        this.EventType = EventType;
    }
}
