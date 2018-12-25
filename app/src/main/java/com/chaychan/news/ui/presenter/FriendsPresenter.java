package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IFriendsListener;

public class FriendsPresenter extends BasePresenter<IFriendsListener> {

    public FriendsPresenter(IFriendsListener view) {
        super(view);
    }

    public void getFriendsList(String authorities) {

        addSubscription(mApiService2.FriendsList("Bearer " + authorities), new SubscriberCallBack<ResultList>() {

            @Override
            protected void onSuccess(ResultList response) {
                mView.onGetFriendsSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
