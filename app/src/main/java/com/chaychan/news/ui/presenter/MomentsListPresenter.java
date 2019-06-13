package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.lMomentsListView;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;


public class MomentsListPresenter extends BasePresenter<lMomentsListView> {

    public MomentsListPresenter(lMomentsListView view) {
        super(view);
    }


    public void getRefreshMomentsList(String publisherUserId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userId", publisherUserId);
        map.put("page", 1);
        map.put("size", 20);

        addSubscription(mApiService2.circleFriendsList("Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDE4MTExNTE0NTU0ODAxNTM5IiwiZXhwIjoxNTU5OTg3NzI3fQ.texHLv6-WzOktJ8Bv-RuFoA2KFbRnCkhkodat0oQDEc", map), new SubscriberCallBack<BasePageEntity<Moments>>() {

            @Override
            protected void onSuccess(BasePageEntity<Moments> response) {

                List<Moments> momentsList = response.getRecords();
                KLog.e(momentsList);
                mView.onGetNewsListSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

    public void getMoreMomentsList(String publisherUserId, int page) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userId", publisherUserId);
        map.put("page", page);
        map.put("size", 20);

        addSubscription(mApiService2.circleFriendsList("Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDE4MTExNTE0NTU0ODAxNTM5IiwiZXhwIjoxNTU5OTg3NzI3fQ.texHLv6-WzOktJ8Bv-RuFoA2KFbRnCkhkodat0oQDEc",map), new SubscriberCallBack<BasePageEntity<Moments>>() {

            @Override
            protected void onSuccess(BasePageEntity<Moments> response) {

                List<Moments> momentsList = response.getRecords();
                KLog.e(momentsList);
                mView.onMoreMomentsSuccess(response);
            }

            @Override
            protected void onError() {
//                mView.onError();
            }
        });
    }
}
