package com.chaychan.news.view;

public interface MomentsReleaseListener<T> {

    void onReleaseSuccess(T response);

    void onUpFileSuccess(T response);

    void onError();

}
