package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.AccountList;
import com.chaychan.news.model.entity.ResponseList;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IFriendsListener;

import java.util.HashMap;

public class AccountsPresenter extends BasePresenter<IFriendsListener> {

    public AccountsPresenter(IFriendsListener view) {
        super(view);
    }

    /**
     * 统计月份记账记录
     */
    public void getMonthBillList(String year, String month) {
        String authorities = MyApp.getInstances().getToken();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("year", year);
        map.put("month", month);
        addSubscription(mApiService2.getMonthBillList("Bearer " + authorities,map), new SubscriberCallBack<ResponseList<AccountList>>() {

            @Override
            protected void onSuccess(ResponseList<AccountList> response) {
                mView.onGetFriendsSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
