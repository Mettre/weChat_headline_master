package com.chaychan.news.ui.presenter;

import android.text.TextUtils;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.enum_.DynamicTypeEnum;
import com.chaychan.news.model.entity.MomentsDetailsEntity;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.MomentsDetailsListener;
import com.socks.library.KLog;

import java.util.HashMap;

public class MomentsDetailsPresenter extends BasePresenter<MomentsDetailsListener> {

    public MomentsDetailsPresenter(MomentsDetailsListener view) {
        super(view);
    }

    /**
     * 说说详情+评论
     *
     * @param momentsId
     */
    public void getRefreshMomentsList(String momentsId) {
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.momentsDetails("Bearer " + authorities, momentsId), new SubscriberCallBack<MomentsDetailsEntity>() {

            @Override
            protected void onSuccess(MomentsDetailsEntity response) {

                mView.onGetListSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

    /**
     * 新增评论
     */
    public void addReplyMoments(String dynamicId, String replyContent, String replyParentId, DynamicTypeEnum dynamicType) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("dynamicId", dynamicId);
        map.put("replyContent", replyContent);
        if (dynamicType != null) {
            map.put("dynamicType", dynamicType.name());
        }
        if (!TextUtils.isEmpty(replyParentId)) {
            map.put("replyParentId", replyParentId);
        }
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.addReply("Bearer " + authorities, map), new SubscriberCallBack<MomentsDetailsEntity>() {

            @Override
            protected void onSuccess(MomentsDetailsEntity response) {

                mView.onAddReplySuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }


    /**
     * 删除评论--只可该说说发布者删除
     */
    public void deleteReplyMoments(String dynamicId) {

        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.deleteReplyMoments("Bearer " + authorities, dynamicId), new SubscriberCallBack<MomentsDetailsEntity>() {

            @Override
            protected void onSuccess(MomentsDetailsEntity response) {

                mView.onAddReplySuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

}
