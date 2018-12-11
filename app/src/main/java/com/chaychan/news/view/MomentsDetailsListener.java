package com.chaychan.news.view;

public interface MomentsDetailsListener<T> {

    void onGetListSuccess(T response);

    void onError();
}
