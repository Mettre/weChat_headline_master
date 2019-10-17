package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.model.entity.Friends;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IFriendsListener;

public class FriendsPresenter extends BasePresenter<IFriendsListener> {

    public FriendsPresenter(IFriendsListener view) {
        super(view);
    }

    /**
     * 我的好友
     * @param authorities
     */
    public void getFriendsList(String authorities) {

        addSubscription(mApiService2.FriendsList("Bearer " + authorities), new SubscriberCallBack<ResultList<Friends>>() {

            @Override
            protected void onSuccess(ResultList<Friends> response) {
                mView.onGetFriendsSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
