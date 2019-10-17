package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.api.SubscriberCallBack2;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.FindUserBean;
import com.chaychan.news.model.entity.FollowBean;
import com.chaychan.news.model.entity.Friends;
import com.chaychan.news.model.entity.ResultList;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.model.response.ResultResponse2;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.FindUserListener;

import java.util.HashMap;
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
        addSubscription(mApiService2.findUserListRequest("Bearer " + authorities, findUserId), new SubscriberCallBack<ResultList<FindUserBean>>() {

            @Override
            protected void onSuccess(ResultList<FindUserBean> response) {
                mView.onGetRefreshListSuccess(response.getList());

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

    /**
     * 添加关注
     */
    public void addFollowRequest(String followedUser) {

        String authorities = MyApp.getInstances().getToken();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("followedUser", followedUser);
        addSubscription(mApiService2.addFollowRequest("Bearer " + authorities, map), new SubscriberCallBack<ResultResponse>() {

            @Override
            protected void onSuccess(ResultResponse response) {
                mView.addFollowSuccess();

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }


    /**
     * 取消关注
     */
    public void cancelFollowRequest(String followedUser) {

        String authorities = MyApp.getInstances().getToken();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("followedUser", followedUser);
        addSubscription(mApiService2.cancelFollowRequest("Bearer " + authorities, map), new SubscriberCallBack<ResultResponse>() {

            @Override
            protected void onSuccess(ResultResponse response) {
                mView.cancelFollowSuccess();

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
