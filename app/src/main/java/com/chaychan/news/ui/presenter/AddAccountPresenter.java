package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.AccountClassification;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.FindUserListener;

public class AddAccountPresenter extends BasePresenter<FindUserListener> {

    public AddAccountPresenter(FindUserListener view) {
        super(view);
    }

    /**
     * 记账类型
     */
    public void classificationList() {
        addSubscription(mApiService2.accountClassification(), new SubscriberCallBack<ResultList<AccountClassification>>() {

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
}
