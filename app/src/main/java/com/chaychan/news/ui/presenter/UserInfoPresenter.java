package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.model.entity.AccountStatistics;
import com.chaychan.news.model.entity.UserInfo;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IRequestListener;
import com.chaychan.news.view.IRequestMineListener;

public class UserInfoPresenter extends BasePresenter<IRequestMineListener> {
    public UserInfoPresenter(IRequestMineListener view) {
        super(view);
    }

    /**
     * 获取个人信息
     *
     * @param authorities
     */
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

    /**
     * 获取个人信息
     *
     * @param authorities
     */
    public void getStatistics(String authorities) {

        addSubscription(mApiService2.getStatistics("Bearer " + authorities), new SubscriberCallBack<AccountStatistics>() {
            @Override
            protected void onSuccess(AccountStatistics response) {
                mView.onRequestSecondSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
