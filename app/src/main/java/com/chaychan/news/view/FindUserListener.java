package com.chaychan.news.view;

/**
 * 查找用户
 * @param <T>
 */
public interface FindUserListener<T> {

    void onGetRefreshListSuccess(T response);

    void onError();

    void addFollowSuccess();

    void cancelFollowSuccess();
}
