package com.chaychan.news.api;

import android.text.TextUtils;

import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.activity.LoginActivity;
import com.chaychan.news.utils.ToastUtils;
import com.chaychan.news.utils.UIUtils;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * @author ChayChan
 * @description: 抽取CallBack
 * @date 2017/6/18  21:37
 */
public abstract class SubscriberCallBack<T> extends Subscriber<ResultResponse<T>> {

    @Override
    public void onNext(ResultResponse response) {
        boolean isSuccess = (!TextUtils.isEmpty(response.message) && response.message.equals("success"))
                || response.success != null && response.success == true;
        if (isSuccess) {
            onSuccess((T) response.data);
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

    protected abstract void onSuccess(T response);

    protected abstract void onError();

    protected void onFailure(ResultResponse response) {
    }

}
