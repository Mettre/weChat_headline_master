package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.model.entity.BaseEntity;
import com.chaychan.news.model.entity.MomentsDetailsEntity;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.MomentsDetailsListener;
import com.socks.library.KLog;

import java.util.HashMap;

public class MomentsDetailsPresenter extends BasePresenter<MomentsDetailsListener> {

    public MomentsDetailsPresenter(MomentsDetailsListener view) {
        super(view);
    }

    public void getRefreshMomentsList(String publisherUserId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("publisherUserId", publisherUserId);
        map.put("page", 1);
        map.put("size", 20);

        addSubscription(mApiService2.PersonalMomentsList(map), new SubscriberCallBack<BaseEntity<MomentsDetailsEntity>>() {

            @Override
            protected void onSuccess(BaseEntity<MomentsDetailsEntity> response) {

                MomentsDetailsEntity momentsDetailsEntity = response.getData();
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
