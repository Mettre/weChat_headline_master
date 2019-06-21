package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.MomentsDetailsEntity;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.MomentsDetailsListener;
import com.socks.library.KLog;

public class MomentsDetailsPresenter extends BasePresenter<MomentsDetailsListener> {

    public MomentsDetailsPresenter(MomentsDetailsListener view) {
        super(view);
    }

    /**
     * 说说详情
     *
     * @param momentsId
     */
    public void getRefreshMomentsList(String momentsId) {
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.momentsDetails("Bearer " + authorities, momentsId), new SubscriberCallBack<MomentsDetailsEntity>() {

            @Override
            protected void onSuccess(MomentsDetailsEntity response) {

                MomentsDetailsEntity momentsDetailsEntity = response;
                KLog.e(momentsDetailsEntity);
                mView.onGetListSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

}
