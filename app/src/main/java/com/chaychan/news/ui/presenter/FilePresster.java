package com.chaychan.news.ui.presenter;

import com.chaychan.news.api.SubscriberCallBack;
import com.chaychan.news.app.MyApp;
import com.chaychan.news.model.entity.LoginBean;
import com.chaychan.news.ui.base.BasePresenter;
import com.chaychan.news.view.IRequestListener;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FilePresster extends BasePresenter<IRequestListener> {

    public FilePresster(IRequestListener view) {
        super(view);
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
        addSubscription(mApiService2.upLoadFile("Bearer " + authorities, builder.build()), new SubscriberCallBack<Object>() {
            @Override
            protected void onSuccess(Object response) {
                mView.onRequestFirstSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }
        });
    }
}
