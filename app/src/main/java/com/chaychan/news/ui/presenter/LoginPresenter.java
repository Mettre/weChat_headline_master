package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IRequestListener;

public class LoginPresenter extends BasePresenter<IRequestListener> {

    public LoginPresenter(IRequestListener view) {
        super(view);
    }

    public void LoginRequest(String phone, String password) {

        addSubscription(mApiService2.loginRequest(phone, password), new SubscriberCallBack<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean response) {
                mView.onRequestFirstSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
