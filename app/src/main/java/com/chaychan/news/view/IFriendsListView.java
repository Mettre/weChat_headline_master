package com.chaychan.news.view;

public interface IFriendsListView<T> {

    void onGetFriendsSuccess(T response);

    void onError();
}
