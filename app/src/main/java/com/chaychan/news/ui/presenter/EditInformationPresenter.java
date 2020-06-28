package com.chaychan.news.ui.presenter;

import android.text.TextUtils;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.enum_.GenderEnum;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IRequestListener;

import java.util.HashMap;

public class EditInformationPresenter extends BasePresenter<IRequestListener> {

    public EditInformationPresenter(IRequestListener view) {
        super(view);
    }

    public void EditInformationRequest(String userName, GenderEnum gender, Integer age, String e_mall) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        if (!TextUtils.isEmpty(userName)) {
            map.put("userName", userName);
        }
        if (gender != null) {
            map.put("gender", gender.name());
        }
        if (age != null) {
            map.put("age", age);
        }
        if (!TextUtils.isEmpty(e_mall)) {
            map.put("e_mall", e_mall);
        }
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.modifyUserInfoRequest("Bearer " + authorities, map), new SubscriberCallBack<Object>() {
            @Override
            protected void onSuccess(Object response) {
                mView.onRequestFirstSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
