package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.FollowBean;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.FollowListener;

import java.util.HashMap;

public class FollowPresenter extends BasePresenter<FollowListener> {

    public FollowPresenter(FollowListener view) {
        super(view);
    }

    /**
     * 我的关注
     */
    public void getRefreshFollowList(int page, int size) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("size", size);

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.RefreshFollowListRequest("Bearer " + authorities, map), new SubscriberCallBack<BasePageEntity<FollowBean>>() {

            @Override
            protected void onSuccess(BasePageEntity<FollowBean> response) {
                mView.onGetRefreshListSuccess(response);

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }


    /**
     * 我的粉丝
     */
    public void getRefreshFansList(int page, int size) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("size", size);

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.RefreshFansListRequest("Bearer " + authorities, map), new SubscriberCallBack<BasePageEntity<FollowBean>>() {

            @Override
            protected void onSuccess(BasePageEntity<FollowBean> response) {
                mView.onGetRefreshListSuccess(response);

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
