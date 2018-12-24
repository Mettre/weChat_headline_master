package com.chaychan.news.api;

import android.text.TextUtils;

import com.chaychan.news.model.response.ResultResponse2;
import com.chaychan.news.utils.UIUtils;
import com.socks.library.KLog;

import java.util.List;

import rx.Subscriber;

public abstract class SubscriberCallBack2<T> extends Subscriber<ResultResponse2<T>> {

    @Override
    public void onNext(ResultResponse2 response) {
        boolean isSuccess = (!TextUtils.isEmpty(response.message) && response.message.equals("success"))
                || !TextUtils.isEmpty(response.success) && response.success.equals("true");
        if (isSuccess) {
            onSuccess((List<T>) response.data);
        } else {
            UIUtils.showToast(response.message);
            onFailure(response);
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        KLog.e(e.getLocalizedMessage());
        onError();
    }

    protected abstract void onSuccess(List<T> response);

    protected abstract void onError();

    protected void onFailure(ResultResponse2 response) {
    }

}
