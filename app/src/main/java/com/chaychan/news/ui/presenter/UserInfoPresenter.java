package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.model.entity.UserInfo;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IRequestListener;

public class UserInfoPresenter extends BasePresenter<IRequestListener> {
    public UserInfoPresenter(IRequestListener view) {
        super(view);
    }

    public void getUserInfo(String authorities) {

        addSubscription(mApiService2.getUserInfo("Bearer " + authorities), new SubscriberCallBack<UserInfo>() {
            @Override
            protected void onSuccess(UserInfo response) {
                mView.onRequestFirstSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
