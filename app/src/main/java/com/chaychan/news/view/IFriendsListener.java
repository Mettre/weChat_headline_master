package com.chaychan.news.view;

public interface IFriendsListener<T> {

    void onGetFriendsSuccess(T response);

    void onError();
}
