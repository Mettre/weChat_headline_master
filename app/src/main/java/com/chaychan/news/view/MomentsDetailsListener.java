package com.chaychan.news.view;

public interface MomentsDetailsListener<T> {

    void onGetListSuccess(T response);

    void onAddReplySuccess(T response);

    void onError();
}
