package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.enum_.SmsTypeEnum;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.ISendRequestListener;

import java.util.HashMap;

public class RegisterPresenter extends BasePresenter<ISendRequestListener> {

    public RegisterPresenter(ISendRequestListener view) {
        super(view);
    }

    public void RegisterRequest(String phone, String password, String captchaCode) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("captchaCode", captchaCode);
        addSubscription(mApiService2.registerRequest(map), new SubscriberCallBack<ResultResponse>() {
            @Override
            protected void onSuccess(ResultResponse response) {
                mView.onRequestFirstSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

    public void verificationRequest(String phone,SmsTypeEnum smsTypeEnum) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("phone", phone);
        map.put("captchaCode", smsTypeEnum.getCode());
        addSubscription(mApiService2.sendMessageRequest(map), new SubscriberCallBack<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean response) {
                mView.onRequestSecondSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
