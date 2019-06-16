package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.enum_.SmsTypeEnum;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.ISendRequestListener;

import java.util.HashMap;

public class ForgetPasswordPresenter extends BasePresenter<ISendRequestListener> {

    public ForgetPasswordPresenter(ISendRequestListener view) {
        super(view);
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @param captchaCode
     */
    public void ForgetPasswordRequest(String phone, String password, String captchaCode) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("captchaCode", captchaCode);
        addSubscription(mApiService2.forgetPasswordRequest(map), new SubscriberCallBack<ResultResponse>() {
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

    /**
     * 修改密码
     *
     * @param newPassword
     * @param oldPassword
     */
    public void ModifyPasswordRequest(String oldPassword, String newPassword) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("newPassword", newPassword);
        map.put("oldPassword", oldPassword);
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.modifyPasswordRequest("Bearer " + authorities, map), new SubscriberCallBack<ResultResponse>() {
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

    /**
     * 发送忘记密码验证码
     *
     * @param phone
     * @param smsTypeEnum
     */
    public void verificationRequest(String phone, SmsTypeEnum smsTypeEnum) {

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
