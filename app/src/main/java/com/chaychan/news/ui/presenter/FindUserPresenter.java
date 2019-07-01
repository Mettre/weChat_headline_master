package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.api.SubscriberCallBack2;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.FindUserBean;
import com.chaychan.news.model.entity.FollowBean;
import com.chaychan.news.model.response.ResultResponse2;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.FindUserListener;

import java.util.List;

public class FindUserPresenter extends BasePresenter<FindUserListener> {

    public FindUserPresenter(FindUserListener view) {
        super(view);
    }

    /**
     * 查找用户
     */
    public void getFindUserList(String findUserId) {

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.findUserListRequest("Bearer " + authorities, findUserId), new SubscriberCallBack2<FindUserBean>() {

            @Override
            protected void onSuccess(List<FindUserBean> response) {
                mView.onGetRefreshListSuccess(response);

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
