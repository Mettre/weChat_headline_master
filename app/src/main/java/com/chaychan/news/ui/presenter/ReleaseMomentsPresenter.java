package com.chaychan.news.ui.presenter;

import android.text.TextUtils;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.model.entity.ReleaseMoments;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.MomentsReleaseListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReleaseMomentsPresenter extends BasePresenter<MomentsReleaseListener> {


    public ReleaseMomentsPresenter(MomentsReleaseListener view) {
        super(view);
    }

    /**
     * 发布说说
     */
    public void releaseMoments(ReleaseMoments releaseMoments) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("momentsTitle", releaseMoments.getMomentsTitle());
        if (releaseMoments.getMomentsType() != null) {
            map.put("momentsType", releaseMoments.getMomentsType().name());
        }
        if (!TextUtils.isEmpty(releaseMoments.getMomentsImage())) {
            map.put("momentsImage", releaseMoments.getMomentsImage());
        }
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.releaseMoments("Bearer " + authorities, map), new SubscriberCallBack<Object>() {

            @Override
            protected void onSuccess(Object response) {

                mView.onReleaseSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }

    /**
     * 上传文件
     *
     * @param fileList
     */
    public void uploadFileRequest(List<File> fileList) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("name", "name");
        for (File f : fileList) {
            builder.addFormDataPart("file", f.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), f));
        }
        String authorities = MyApp.getInstances().getToken();
        addSubscription(mApiService2.upLoadFile("Bearer " + authorities, builder.build()), new SubscriberCallBack<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean response) {
                mView.onUpFileSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
