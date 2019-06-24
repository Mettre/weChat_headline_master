package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.FeedbackBean;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.PageListListener;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;

public class PageListPresenter extends BasePresenter<PageListListener> {

    public PageListPresenter(PageListListener view) {
        super(view);
    }

    /**
     * 我的反馈列表刷新
     */
    public void getRefreshFeedbackList(int page, int size) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("size", size);

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.findFeedbackListRequest("Bearer " + authorities, map), new SubscriberCallBack<BasePageEntity<FeedbackBean>>() {

            @Override
            protected void onSuccess(BasePageEntity<FeedbackBean> response) {

                mView.onGetRefreshListSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

    /**
     * 我的反馈列表拉取更多
     */
    public void getMoreFeedbackList(int page, int size) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("size", size);

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.findFeedbackListRequest("Bearer " + authorities, map), new SubscriberCallBack<BasePageEntity<FeedbackBean>>() {

            @Override
            protected void onSuccess(BasePageEntity<FeedbackBean> response) {

                List<FeedbackBean> momentsList = response.getRecords();
                KLog.e(momentsList);
                mView.onMoreListSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }


}
