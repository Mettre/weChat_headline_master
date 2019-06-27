package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.FollowBean;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.FindUserListener;

public class FindUserPresenter extends BasePresenter<FindUserListener> {

    public FindUserPresenter(FindUserListener view) {
        super(view);
    }

    /**
     * 查找用户
     */
    public void getfindUserList(String findUserId) {

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.findUserListRequest("Bearer " + authorities, findUserId), new SubscriberCallBack<BasePageEntity<FollowBean>>() {

            @Override
            protected void onSuccess(BasePageEntity<FollowBean> response) {
                mView.onGetRefreshListSuccess(response);

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
