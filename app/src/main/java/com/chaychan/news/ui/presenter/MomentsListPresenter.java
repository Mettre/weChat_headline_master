package com.chaychan.news.ui.presenter;

import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.entity.NewsData;
import com.chaychan.news.model.response.NewsResponse;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.utils.ListUtils;
import com.chaychan.news.utils.PreUtils;
import com.chaychan.news.view.lMomentsListView;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class MomentsListPresenter extends BasePresenter<lMomentsListView> {

    private long lastTime;

    public MomentsListPresenter(lMomentsListView view) {
        super(view);
    }


    public void getNewsList(String channelCode) {
        lastTime = PreUtils.getLong(channelCode, 0);//读取对应频道下最后一次刷新的时间戳
        if (lastTime == 0) {
            //如果为空，则是从来没有刷新过，使用当前时间戳
            lastTime = System.currentTimeMillis() / 1000;
        }

        addSubscription(mApiService.getNewsList(channelCode, lastTime, System.currentTimeMillis() / 1000), new Subscriber<NewsResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                KLog.e(e.getLocalizedMessage());
                mView.onError();
            }

            @Override
            public void onNext(NewsResponse response) {
                lastTime = System.currentTimeMillis() / 1000;
                PreUtils.putLong(channelCode, lastTime);//保存刷新的时间戳

                List<NewsData> data = response.data;
                List<Moments> momentsList = new ArrayList<>();
                if (!ListUtils.isEmpty(data)) {
                    for (NewsData newsData : data) {
                        Moments moments = new Gson().fromJson(newsData.content, Moments.class);
                        momentsList.add(moments);
                    }
                }
                KLog.e(momentsList);
                mView.onGetNewsListSuccess(momentsList, response.tips.display_info);
            }
        });
    }
}
