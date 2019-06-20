package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.lMomentsListView;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;

/**
 * 我的朋友的朋友圈
 */
public class MomentsListPresenter extends BasePresenter<lMomentsListView> {

    public MomentsListPresenter(lMomentsListView view) {
        super(view);
    }

    public void getRefreshMomentsList() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", 1);
        map.put("size", 20);

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.circleFriendsList("Bearer " + authorities, map), new SubscriberCallBack<BasePageEntity<Moments>>() {

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

    public void getMoreMomentsList(int page) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("size", 20);

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.circleFriendsList("Bearer " + authorities, map), new SubscriberCallBack<BasePageEntity<Moments>>() {

            @Override
            protected void onSuccess(BasePageEntity<Moments> response) {

                List<Moments> momentsList = response.getRecords();
                KLog.e(momentsList);
                mView.onMoreMomentsSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
