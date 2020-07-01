package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.AccountClassification;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.FindUserListener;

import java.math.BigDecimal;
import java.util.HashMap;

public class AddAccountPresenter extends BasePresenter<FindUserListener> {

    public AddAccountPresenter(FindUserListener view) {
        super(view);
    }

    /**
     * 记账类型
     */
    public void classificationList(int type) {
        addSubscription(mApiService2.accountClassification(type), new SubscriberCallBack<ResultList<AccountClassification>>() {

            @Override
            protected void onSuccess(ResultList<AccountClassification> response) {
                mView.onGetRefreshListSuccess(response);

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

    /**
     * 添加记账
     */
    public void addAccount(String title, BigDecimal amount, String classification, Integer type, String recordDay) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", title);
        map.put("amount", amount);
        map.put("classification", classification);
        map.put("type", type);
        map.put("recordDay", recordDay);
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.addAccount("Bearer " + authorities, map), new SubscriberCallBack<Object>() {

            @Override
            protected void onSuccess(Object response) {
                mView.addFollowSuccess();

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
