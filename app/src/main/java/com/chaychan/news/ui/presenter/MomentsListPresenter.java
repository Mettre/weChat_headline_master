package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.NewsData;
import com.chaychan.news.model.entity.NewsDetail;
import com.chaychan.news.model.response.NewsResponse;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.PreUtils;
import com.chaychan.news.view.lMomentsListView;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;

public class MomentsListPresenter extends BasePresenter<lMomentsListView> {

    private long lastTime;

    public MomentsListPresenter(lMomentsListView view) {
        super(view);
    }


    public void getMomentsList(String publisherUserId) {
        lastTime = PreUtils.getLong(publisherUserId, 0);//读取对应频道下最后一次刷新的时间戳
        if (lastTime == 0) {
            //如果为空，则是从来没有刷新过，使用当前时间戳
            lastTime = System.currentTimeMillis() / 1000;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("page", "1");
        map.put("size", "10");
        map.put("userId", publisherUserId);

        addSubscription(mApiService2.circleFriendsList(map), new SubscriberCallBack<BasePageEntity<Moments>>() {

            @Override
            protected void onSuccess(BasePageEntity<Moments> response) {
                KLog.e("----------Request Start----------------");
                lastTime = System.currentTimeMillis() / 1000;
                PreUtils.putLong(publisherUserId, lastTime);//保存刷新的时间戳

                List<Moments> momentsList = response.getRecords();
                KLog.e(momentsList);
                mView.onGetNewsListSuccess(momentsList, "微信头条推荐引擎有15条更新");
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
