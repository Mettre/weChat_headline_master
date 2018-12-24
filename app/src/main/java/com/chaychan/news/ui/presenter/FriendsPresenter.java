package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IFriendsListView;

public class FriendsPresenter extends BasePresenter<IFriendsListView> {

    public FriendsPresenter(IFriendsListView view) {
        super(view);
    }

    public void getFriendsList(String authorities) {

        addSubscription(mApiService2.FriendsList("Bearer " + authorities), new SubscriberCallBack<ResultList>() {

            @Override
            protected void onSuccess(ResultList response) {
                mView.onGetFriendsSuccess(response.getList());
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
