package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.BasePageEntity;
import com.chaychan.news.model.entity.VisitorBean;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.VisitorListListener;

import java.util.HashMap;

public class VisitorListPresenter extends BasePresenter<VisitorListListener> {

    public VisitorListPresenter(VisitorListListener view) {
        super(view);
    }

    /**
     * 我的访客记录
     */
    public void getRefreshVisitorList(int page, int size) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("size", size);

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.RefreshVisitorListRequest("Bearer " + authorities, map), new SubscriberCallBack<BasePageEntity<VisitorBean>>() {

            @Override
            protected void onSuccess(BasePageEntity<VisitorBean> response) {
                mView.onGetRefreshListSuccess(response);

            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }


    /**
     * 主人删除空间访问某个人记录
     */
    public void deleteVisitor(long visitorId, int position) {
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.deleteVisitorRequest("Bearer " + authorities, visitorId), new SubscriberCallBack<ResultResponse>() {

            @Override
            protected void onSuccess(ResultResponse response) {
                mView.onDeleteSuccess(position);
            }

            @Override
            protected void onError() {
                mView.onDeleteError();
            }
        });
    }

}
