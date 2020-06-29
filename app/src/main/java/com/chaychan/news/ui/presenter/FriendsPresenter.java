package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.Friends;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IFriendsListener;

public class FriendsPresenter extends BasePresenter<IFriendsListener> {

    public FriendsPresenter(IFriendsListener view) {
        super(view);
    }

    /**
     * 我的好友
     */
    public void getFriendsList() {
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.FriendsList("Bearer " + authorities), new SubscriberCallBack<Friends>() {

            @Override
            protected void onSuccess(Friends response) {
                mView.onGetFriendsSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
