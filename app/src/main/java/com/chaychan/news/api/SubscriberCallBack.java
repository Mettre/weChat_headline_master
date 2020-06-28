package com.chaychan.news.api;

import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.activity.LoginActivity;
import com.chaychan.news.utils.UIUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * @author ChayChan
 * @description: 抽取CallBack
 * @date 2017/6/18  21:37
 */
public abstract class SubscriberCallBack<T> extends Subscriber<ResultResponse<T>> {

    @Override
    public void onNext(ResultResponse response) {
        if (response.code == 0) {
            onSuccess((T) response.result);
        } else {
            if (401 == response.code) {
                LoginActivity.startLoginActivity(UIUtils.getContext());
                EventBus.getDefault().post(new StartBrotherEvent(StartBrotherEvent.LOUGINOUT));
            }
            UIUtils.showToast(response.msg);
            onFailure(response);
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        KLog.e(e.getMessage());
        UIUtils.showToast(e.getMessage());
        onError();
    }

    protected abstract void onSuccess(T response);

    protected abstract void onError();

    protected void onFailure(ResultResponse response) {
    }

}
