package com.chaychan.news.api;

import android.text.TextUtils;

import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.response.ResultResponse2;
import com.chaychan.news.ui.activity.LoginActivity;
import com.chaychan.news.utils.UIUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;

public abstract class SubscriberCallBack2<T> extends Subscriber<ResultResponse2<T>> {

    @Override
    public void onNext(ResultResponse2 response) {
        boolean isSuccess = (!TextUtils.isEmpty(response.message) && response.message.equals("success"))
                || response.success != null && response.success == true;
        if (isSuccess) {
            onSuccess((List<T>) response.data);
        } else {
            if ("401".equals(response.code)) {
                LoginActivity.startLoginActivity(UIUtils.getContext());
                EventBus.getDefault().post(new StartBrotherEvent(StartBrotherEvent.LOUGINOUT));
            }
            UIUtils.showToast(response.message);
//            new SweetAlertDialog(UIUtils.getContext(), SweetAlertDialog.ERROR_TYPE)
//                    .setTitleText("错误")
//                    .setContentText(response.message)
//                    .show();
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
//        new SweetAlertDialog(UIUtils.getContext(), SweetAlertDialog.ERROR_TYPE)
//                .setTitleText("错误")
//                .setContentText(e.getLocalizedMessage())
//                .show();
//        onError();
    }

    protected abstract void onSuccess(List<T> response);

    protected abstract void onError();

    protected void onFailure(ResultResponse2 response) {
    }
}
