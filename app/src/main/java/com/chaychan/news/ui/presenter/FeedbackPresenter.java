package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.Moments;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IRequestListener;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;

/**
 * 用户反馈
 */
public class FeedbackPresenter extends BasePresenter<IRequestListener> {


    public FeedbackPresenter(IRequestListener view) {
        super(view);
    }

    /**
     * 用户反馈
     *
     * @param content
     */
    public void addFeedbackRequest(String content) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("content", content);
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.FeedbackRequest("Bearer " + authorities, map), new SubscriberCallBack<Object>() {
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
